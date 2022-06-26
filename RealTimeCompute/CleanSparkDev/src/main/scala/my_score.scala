import org.apache.spark.sql.SparkSession

object my_score {
    def main(args: Array[String]): Unit = {

      val spark = SparkSession
        .builder()
        .appName("Score Statistics")
        .config("spark.master", "local")
        .getOrCreate()

      val df = spark.read.option("header", "true").csv("src/main/resources/data.csv") // 此处需要修改为你的文件路径
      // df.show()

      // df.filter("df('bfzcj')<60").show(); // Undefined function: 'df'

      // 列 'bfzcj' 为成绩，其它列都不重要
      import spark.implicits._ // This import is needed to use the $-notation
      df.filter($"bfzcj" < 60).show()
      df.withColumn("bfzcj", $"bfzcj".cast("int")).sort($"bfzcj".desc).show()
    }
}
