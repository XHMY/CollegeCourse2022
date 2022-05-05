import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WriteScore2File {
    private final int stu_num;
    private final int subject_num;
    private final String fileName;

    public WriteScore2File(int stu_num, int subject_num, String fileName) {
        this.stu_num = stu_num;
        this.subject_num = subject_num;
        this.fileName = fileName;
    }

    private Map<Integer, List<Float>> getRandomScore() {
        Map<Integer, List<Float>> stu_scores_result = new HashMap<>();
        for (int i = 0; i < stu_num; i++) {
            List<Float> scores = new ArrayList<>();
            for (int j = 0; j < subject_num; j++) {
                float score = (float) (Math.random() * 100);
                scores.add(score);
            }
            stu_scores_result.put(i, scores);
        }
        return stu_scores_result;
    }

    public void WriteScore2JSONFile() {
        Map<Integer, List<Float>> stu_scores = getRandomScore();
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < stu_num; i++) {
            sb.append("\"" + i + "\": [");
            for (int j = 0; j < subject_num; j++) {
                sb.append(stu_scores.get(i).get(j));
                if (j != subject_num - 1) {
                    sb.append(", ");
                }
            }
            sb.append("],\n");
        }
        sb.append("}");
        String json = sb.toString();

        // Write to file
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write(json);
            writer.close();
    } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void WriteScore2SimpleTextFile() {
        Map<Integer, List<Float>> stu_scores = getRandomScore();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stu_num; i++) {
            for (int j = 0; j < subject_num; j++) {
                sb.append(stu_scores.get(i).get(j));
                sb.append(" ");
            }
            sb.append("\n");
        }
        String text = sb.toString();
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write(text);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        String fileName = "src/main/resources/scores.txt";
        WriteScore2File writeScore2File = new WriteScore2File(10, 5, fileName);
        writeScore2File.WriteScore2SimpleTextFile();
        return;
    }
}
