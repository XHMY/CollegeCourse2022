import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{DoubleType, IntegerType, StringType, StructField, StructType}

import java.util.Properties

object hw1_jdbc {
  def main(args: Array[String]): Unit = {

    // 1. 配置 Spark 启动任务
    var conf=new SparkConf().setAppName("rs").setMaster("local");
    var sc=new SparkContext(conf);
    var spark=SparkSession.builder().getOrCreate();

    // 2. 创建 RDD 数据
    var ar=Array("1001 yokey 75","1002 admin 90","1003 toor 100","1004 root 89","1002 da 97");//声明数组
    var rdd=sc.makeRDD(ar);
    var rs=rdd.map(x=>x.split(" "));
    val schema = StructType(List(StructField("id", IntegerType, true),StructField("name", StringType, true),StructField("chinese", DoubleType, true)));
    val rowRDD = rs.map(p => Row(p(0).toInt, p(1).trim, p(2).toDouble));
    val studentDF = spark.createDataFrame(rowRDD, schema);
    studentDF.createOrReplaceTempView("students_score");


    //下面创建一个prop变量用来保存JDBC连接参数
    val prop = new Properties()
    prop.put("user", "root") //表示用户名是root
    prop.put("driver","com.mysql.jdbc.Driver");

    // 增加数据
    val ADDresults = spark.sql("SELECT * FROM students_score");
    ADDresults.show()
    studentDF.write.mode("append").jdbc("jdbc:mysql://localhost:3306/tmp", "students_score", prop)

    // 删除数据
    val DELETEresults = spark.sql("SELECT * FROM students_score WHERE id != 1001");
    DELETEresults.show()
    DELETEresults.write.mode("overwrite").jdbc("jdbc:mysql://localhost:3306/tmp", "students_score", prop)

    // 改数据
    val studentDF_Update = studentDF.withColumn("chinese", studentDF.col("chinese")-10)
    studentDF_Update.show()
    studentDF_Update.write.mode("overwrite").jdbc("jdbc:mysql://localhost:3306/tmp", "students_score", prop)

    // 查数据
    val SELECTresults = spark.sql("SELECT * FROM students_score WHERE chinese == 100");
    SELECTresults.show()
    SELECTresults.write.mode("overwrite").jdbc("jdbc:mysql://localhost:3306/tmp", "students_score", prop)

  }

}
