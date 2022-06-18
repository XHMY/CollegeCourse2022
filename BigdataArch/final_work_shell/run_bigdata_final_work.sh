# 启动 Hadoop
cd $HADOOP_HOME
./sbin/start-all.sh

# 启动 openresty
brew services run openresty

# 启动 Hive
brew services run mysql
cd $HIVE_HOME
$HIVE_HOME/bin/hiveserver2 &
$HIVE_HOME/bin/hive --service metastore &

# 启动 flume
cd ~/Code/CollegeCourse2022/BigdataArch/flume_work
flume-ng agent --conf ./conf/ -f conf/hive.conf  -n a1 --classpath "$HIVE_HOME/lib/*:$HIVE_HOME/hcatalog/share/hcatalog/*" &

# 启动 superset
export FLASK_APP=superset
superset run -p 9099 --with-threads --reload &