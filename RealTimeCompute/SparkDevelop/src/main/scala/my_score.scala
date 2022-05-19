import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

object my_score {
    def main(args: Array[String]): Unit = {
      var conf=new SparkConf().setAppName("my_score").setMaster("local[*]")

      var sc=new SparkContext(conf)
      val spark = SparkSession
        .builder()
//        .appName("Score Statistics")
//        .config("spark.master", "local")
        .getOrCreate()

      val df = spark.read.option("header", "true").csv("C:/Users/zyf6/Documents/GitHub/CollegeCourse2022/DataVisualize/成绩/data.csv")
      df.show()

//      spark.sql("SELECT * FROM global_temp.people").show()
      df.filter("df('bfzcj')<60").show();
    }
}
