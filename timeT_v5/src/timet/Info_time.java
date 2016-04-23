package timet;

import javafx.beans.property.SimpleStringProperty;

public class Info_time {
        private final SimpleStringProperty dayi;
        private final SimpleStringProperty period1;
        private final SimpleStringProperty period2;
        private final SimpleStringProperty period3;
        private final SimpleStringProperty period4;
        private final SimpleStringProperty lab;

        Info_time(String day, String p1, String p2, String p3, String p4,String lab) {
            this.dayi = new SimpleStringProperty(day);
            this.period1 = new SimpleStringProperty(p1);
            this.period2 = new SimpleStringProperty(p2);
            this.period3 = new SimpleStringProperty(p3);
            this.period4 = new SimpleStringProperty(p4);
            this.lab = new SimpleStringProperty(lab);
        }

        
        public String getLab() {
            return lab.get();
        }

        public void setLab(String lab) {
            this.lab.set(lab);
        }
        
        public String getDayi() {
            return dayi.get();
        }

        public void setDayi(String day) {
            this.dayi.set(day);
        }

        public String getPeriod1() {
            return period1.get();
        }

        public void setPeriod1(String p1) {
            period1.set(p1);
        }

        public String getPeriod2() {
            return period2.get();
        }

        public void setPeriod2(String p2) {
            period2.set(p2);
        }

        public String getPeriod3() {
            return period3.get();
        }

        public void setPeriod3(String p3) {
            period3.set(p3);
        }

        public String getPeriod4() {
            return period4.get();
        }

        public void setPeriod4(String p4) {
            period4.set(p4);
        }

    }