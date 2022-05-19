import org.apache.spark.sql.SparkSession

object score {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("Score Statistics")
      .getOrCreate()

    val df = spark.read.csv("C:/Users/zyf6/Documents/GitHub/CollegeCourse2022/DataVisualize/成绩/data.csv")
    df.show()
  }
}
