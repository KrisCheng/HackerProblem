import bean.CallTimeBean;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kris Peng on 19:52 27/12/2017 .
 * All right reserved.
 */
public class TimeRate {

    public static void switchTimeslot(CallTimeBean timeBean, String operator){
        if(operator.equals("00")||operator.equals("01")||operator.equals("02")){
            timeBean.timeSlot1++;
        }
        else if(operator.equals("03")||operator.equals("04")||operator.equals("05")){
            timeBean.timeSlot2++;
        }
        else if(operator.equals("06")||operator.equals("07")||operator.equals("08")){
            timeBean.timeSlot3++;
        }
        else if(operator.equals("09")||operator.equals("10")||operator.equals("11")){
            timeBean.timeSlot4++;
        }
        else if(operator.equals("12")||operator.equals("13")||operator.equals("14")){
            timeBean.timeSlot5++;
        }
        else if(operator.equals("15")||operator.equals("16")||operator.equals("17")){
            timeBean.timeSlot6++;
        }
        else if(operator.equals("18")||operator.equals("19")||operator.equals("20")){
            timeBean.timeSlot7++;
        }
        else if(operator.equals("21")||operator.equals("22")||operator.equals("23")){
            timeBean.timeSlot8++;
        }
    }

    public static void main(String[] args) {
        //<主叫号码,通话日期>
        HashMap<String, CallTimeBean> timeDistribution = new HashMap<String, CallTimeBean>();
        String FilePath = "dataset/tb_call_201202_random.txt";
        try {
            // read file content from file
            StringBuffer sb = new StringBuffer("");
            FileReader reader = new FileReader(FilePath);
            BufferedReader br = new BufferedReader(reader);
            String str = null;
            while ((str = br.readLine()) != null) {
                str += "\r\n";
                String[] dictionary = str.split("\\s{2,}|\t");
                // 1 --> 主叫号码 9 --> 开始时间 11 --> 通话时长
                String timeStamp = dictionary[9].substring(0,2);
                if(timeDistribution.containsKey(dictionary[1])) {
                    switchTimeslot(timeDistribution.get(dictionary[1]),timeStamp);
                }
                else{
                    CallTimeBean tempBean = new CallTimeBean();
                    switchTimeslot(tempBean,timeStamp);
                    timeDistribution.put(dictionary[1], tempBean);
                }
            }
//            for(Map.Entry<String, CallTimeBean> entry : timeDistribution.entrySet()) {
//                System.out.println(entry.getKey()+" -- "
//                        + " 0~3: " + entry.getValue().timeSlot1
//                        + " 3~6: " + entry.getValue().timeSlot2
//                        + " 6~9: " + entry.getValue().timeSlot3
//                        + " 9~12: " + entry.getValue().timeSlot4
//                        + " 12~15: " + entry.getValue().timeSlot5
//                        + " 15~18: " + entry.getValue().timeSlot6
//                        + " 18~21: " + entry.getValue().timeSlot7
//                        + " 21~24: " + entry.getValue().timeSlot8);
//            }
                br.close();
                reader.close();

            // 结果写入Txt文件
            File writename = new File("time_distribution.txt");
            writename.createNewFile();
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
            for(Map.Entry<String, CallTimeBean> entry : timeDistribution.entrySet()) {
                out.write(entry.getKey()+" -- "
                        + " 0~3: " + entry.getValue().timeSlot1
                        + " 3~6: " + entry.getValue().timeSlot2
                        + " 6~9: " + entry.getValue().timeSlot3
                        + " 9~12: " + entry.getValue().timeSlot4
                        + " 12~15: " + entry.getValue().timeSlot5
                        + " 15~18: " + entry.getValue().timeSlot6
                        + " 18~21: " + entry.getValue().timeSlot7
                        + " 21~24: " + entry.getValue().timeSlot8 +'\n');
            }
            out.flush();
            out.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
