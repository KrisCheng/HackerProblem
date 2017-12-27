import bean.CallTypeBean;

import java.io.*;
/**
 * Created by Kris Peng on 17:50 27/12/2017 .
 * All right reserved.
 */
public class TypeRate {

    public static void switchOperator(CallTypeBean typeBean, String operator){
        if(operator.equals("1")){
            typeBean.telecomNum++;
        }
        else if(operator.equals("2")){
            typeBean.mobileNum++;
        }
        else if(operator.equals("3")){
            typeBean.unicomNum++;
        }
        else{
            typeBean.others++;
        }
    }

    public static void main(String[] args) {
        String FilePath = "dataset/tb_call_201202_random.txt";
        CallTypeBean localCall  = new CallTypeBean();
        CallTypeBean longCall  = new CallTypeBean();
        CallTypeBean roamCall  = new CallTypeBean();
        try {
            // read file content from file
            FileReader reader = new FileReader(FilePath);
            BufferedReader br = new BufferedReader(reader);
            String str = null;
            while ((str = br.readLine()) != null) {
                str += "\r\n";
                String[] dictionary = str.split("\\s{2,}|\t");
                // 3 --> 主叫号码运营商 4 --> 被叫号码运营商 12 --> 通话类型
                //以被叫号码运营商为准
                if(dictionary[12].equals("1")){
                    localCall.all++;
                    switchOperator(localCall,dictionary[4]);
                }
                else if(dictionary[12].equals("2")){
                    longCall.all++;
                    switchOperator(longCall,dictionary[4]);

                }
                else if(dictionary[12].equals("3")){
                    roamCall.all++;
                    switchOperator(roamCall,dictionary[4]);
                }
            }
            System.out.println("市话分布 -- " + "总计: " + localCall.all +" 移动: " + localCall.mobileNum+ " 联通: " + localCall.unicomNum + " 电信: " + localCall.telecomNum + " 其他: " + localCall.others);
            System.out.println("长途分布 -- " + "总计: " + longCall.all+ " 移动: " + longCall.mobileNum + " 联通: " + longCall.unicomNum + " 电信: " + longCall.telecomNum + " 其他: " + longCall.others);
            System.out.println("国际分布 -- " + "总计: " + roamCall.all+ " 移动: " + roamCall.mobileNum + " 联通: " + roamCall.unicomNum + " 电信: " + roamCall.telecomNum + " 其他: " + roamCall.others);

            br.close();
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

