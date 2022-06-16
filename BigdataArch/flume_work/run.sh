rm -rf logs/flume.log
flume-ng agent --conf ./conf/ -f conf/hive.conf  -n a1 --classpath "$HIVE_HOME/lib/*:$HIVE_HOME/hcatalog/share/hcatalog/*"
