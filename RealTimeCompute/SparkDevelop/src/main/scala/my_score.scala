import org.apache.spark.sql.SparkSession

object my_score {
    def main(args: Array[String]): Unit = {

      val spark = SparkSession
        .builder()
        .appName("Score Statistics")
        .config("spark.master", "local")
        .getOrCreate()

      val df = spark.read.option("header", "true").csv("../../DataVisualize/成绩/data.csv")
      // df.show()

      // df.filter("df('bfzcj')<60").show(); // Undefined function: 'df'

      import spark.implicits._ // This import is needed to use the $-notation
      df.filter($"bfzcj" < 60).show()
      df.withColumn("bfzcj", $"bfzcj".cast("int")).sort($"bfzcj".desc).show()
    }
}
