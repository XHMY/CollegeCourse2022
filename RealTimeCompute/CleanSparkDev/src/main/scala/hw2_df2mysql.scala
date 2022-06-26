import org.apache.spark.sql.SparkSession

object hw2_df2mysql {
  def main(args: Array[String]) {
  // read a csv file to dataframe and write to mysql
    val spark = SparkSession.builder.appName("hw2_df2mysql").getOrCreate()
    val df = spark.read.format("csv").option("header", "true").load("src/main/resources/data.csv")
    df.write.format("jdbc")
      .option("url", "jdbc:mysql://localhost:3306/tmp")
      .option("dbtable", "df2mysql")
      .option("user", "root")
      .save()
  }

}
