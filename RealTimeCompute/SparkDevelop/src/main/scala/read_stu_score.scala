import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession



object read_stu_score {

  def main(args: Array[String]): Unit = {
    var csv_file_path = "src/main/resources/stu_score_hard.csv" // Number,Name, Score1, Score2, Score3
    val spark = SparkSession
      .builder()
      .appName("Spark SQL basic example")
      .config("spark.some.config.option", "some-value")
      .getOrCreate()

    val df = spark.read.format("csv").option("header", "true").load(csv_file_path)
    df.show()
    df.printSchema()

    // Write to MySQL
    df.write.format("jdbc").option("url", "jdbc:mysql://localhost:3306/test").option("dbtable", "stu_score").option("user", "root").option("password", "123456").mode("append").save()

  }

}
