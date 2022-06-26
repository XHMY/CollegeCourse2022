import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Thread.sleep;

// 第三次作业，间隔一段时间写入新的文件
public class FeedSteaming {
    public static void main(String[] args) throws InterruptedException {
        // Write line to a new file
        String[] lines = {"Z2099009,坦克驾驶,91,通识课", "Z2099010,飞碟操纵,91,通识课", "Z2099011,火箭炮操纵,91,通识课", "Z2099012,机枪射击实战,99,专业课"};
        Integer cnt = 0; // 计数器
        for (String line : lines) {
            try {
                FileWriter writer = new FileWriter("src/main/resources/file"+ cnt +".txt", false); // 创建文件
                writer.write(line + "\n"); // 写入文件
                writer.close(); // 关闭文件
            } catch (IOException e) {
                e.printStackTrace();
            }
            cnt++;
            sleep(3000);
        }
    }
}
