package util;

public class Test {
    public String isPalindromeLine(String str) {
        StringBuilder strB = new StringBuilder(str);
        StringBuilder strB1 = new StringBuilder();
        for (int i=0;i < strB.length();i++){
            if(strB.substring(i,i+1).isBlank()){
                strB.delete(i,i+1);
                i=i-1;
            }

        }
        //System.out.println(strB.toString());
        //StringBuilder strBr = new StringBuilder(strB);
        //return strBr.reverse().toString().toLowerCase().equals(strB.toString().toLowerCase());
        return strB.toString();
    }
}

