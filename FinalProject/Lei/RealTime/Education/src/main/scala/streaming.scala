import org.apache.spark._
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.streaming._

object streaming {
  def main(args: Array[String]): Unit = {

    // 4. 流计算模拟本学期成绩追加写入数据库
    // 请提前在命令行执行 `nc -lk 9999` 创建向 9999 端口塞数据的netcat连接
    // spark steaming 启动后在命令行nc中输入成绩信息（以,分隔），并回车
    // 每 10 秒程序会将数据写入 MySQL 数据库中

    // Generate the schema based on the string of schema
    val fields = "kch,kcmc,bfzcj,type".split(",")
      .map(fieldName => StructField(fieldName, StringType, nullable = true))
    val schema = StructType(fields)

    // Create a local StreamingContext with two working thread and batch interval of 1 second.
    // The master requires 2 cores to prevent a starvation scenario.
    val conf = new SparkConf().setMaster("local[2]").setAppName("NetworkScore")
    val ssc = new StreamingContext(conf, Seconds(10))

    val lines = ssc.socketTextStream("localhost", 9999)

    lines.foreachRDD(rdd => {
      val spark = SparkSession.builder.config(rdd.sparkContext.getConf).getOrCreate()
      val rowRDD = rdd.map(_.split(",")).map(attributes => Row(attributes(0), attributes(1), attributes(2), attributes(3)))
      val scoreDF = spark.createDataFrame(rowRDD, schema)
      scoreDF.show()
      scoreDF.write.format("jdbc")
        .option("driver", "com.mysql.jdbc.Driver")
        .option("url", "jdbc:mysql://localhost:3306/score")
        .option("dbtable", "yokey_zcst_score")
        .option("user", "root")
        .mode("append")
        .save()
    })

    ssc.start()
    ssc.awaitTermination()
  }

}
