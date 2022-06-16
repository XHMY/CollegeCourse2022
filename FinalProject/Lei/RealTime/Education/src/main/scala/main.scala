import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, SparkSession}

object main {
  def main(args: Array[String]): Unit = {
    elegant_method()
  }

  def elegant_method(): Unit = {
    val spark = SparkSession
      .builder()
      .appName("Score Statistics")
      .config("spark.master", "local")
      .getOrCreate()

    // 利用 Spark SQL 一键读取，过于简洁！！！
    var df = spark.read.option("header", "true").csv("../data.txt")

    import spark.implicits._ // This import is needed to use the $-notation
    df = df.withColumn("bfzcj", $"bfzcj".cast("int")).sort($"bfzcj".desc)

    // 求平均成绩
    println("平均成绩：")
    df.groupBy("type").avg("bfzcj").show()

    // 专业课的成绩信息到MySQL中
    df2mysql(df.filter($"type" === "专业课"))

  }

  // 通过应用程序添加表头用的表头结构
  def get_schema(): StructType = {
    val schemaString = "kch,kcmc,bfzcj,type"

    // Generate the schema based on the string of schema
    val fields = schemaString.split(",")
      .map(fieldName => StructField(fieldName, StringType, nullable = true))
    StructType(fields)
  }

  def df2mysql(df: DataFrame): Unit = {
    df.write.format("jdbc")
      .option("driver", "com.mysql.jdbc.Driver")
      .option("url", "jdbc:mysql://localhost:3306/score")
      .option("dbtable", "yokey_zcst_score")
      .option("user", "root")
      .mode("overwrite")
      .save()
  } //.option("password", "toor")
}
