package final_02_2025211229;

import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Student {
    String name;
    int mid;
    int fin;
    int report;
    int total;
    String grade;

    Student(String name, int mid, int fin, int report) {
        this.name = name;
        this.mid = mid;
        this.fin = fin;
        this.report = report;
        this.total = mid + fin + report;
        this.grade = getGrade(total);
    }

    String getGrade(int score) {
        if (score >= 95) return "A+";
        else if (score >= 90) return "A0";
        else if (score >= 85) return "B+";
        else if (score >= 80) return "B0";
        else if (score >= 75) return "C+";
        else if (score >= 70) return "C0";
        else if (score >= 65) return "D+";
        else if (score >= 60) return "D0";
        else return "F";
    }

    public String toString() {
        return name + "," + mid + "," + fin + "," + report + "," + total + "," + grade;
    }
}

class Calculator extends JFrame {
    JTextField display = new JTextField();

    Calculator() {
        setTitle("계산기 프로그램");
        setSize(300, 350);
        setLayout(new BorderLayout());

        display.setHorizontalAlignment(JTextField.RIGHT);
        add(display, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4));

        String[] buttons = {
                "0", "1", "2", "3",
                "4", "5", "6", "7",
                "8", "9", "CE", "계산",
                "+", "-", "*", "/"
        };

        for (String text : buttons) {
            JButton btn = new JButton(text);
            panel.add(btn);

            btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String value = e.getActionCommand();

                    if (value.equals("CE")) {
                        display.setText("");
                    } else if (value.equals("계산")) {
                        display.setText("계산 버튼 클릭");
                    } else {
                        display.setText(display.getText() + value);
                    }
                }
            });
        }

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }
}

public class Main {
    static Scanner sc = new Scanner(System.in);
    static HashMap<String, Student> map = new HashMap<>();

    public static void main(String[] args) {
        new File("c:\\temp").mkdirs();

        while (true) {
        	System.out.println("[ 본인 소개와 성적관리_계산기프레임_친구정보목록 ]");
            System.out.println();
            System.out.println("1. 자기 소개");
            System.out.println("2. 학생성적 입력");
            System.out.println("3. 학생성적 파일 출력");
            System.out.println("4. 학생 검색");
            System.out.println("5. 계산기 프레임");
            System.out.println("6. 친구 주소록 저장, 출력");
            System.out.println("7. 끝내기");
            System.out.print(" ");

            int menu = sc.nextInt();
            sc.nextLine();

            switch (menu) {
                case 1:
                    introduce();
                    break;
                case 2:
                    inputScore();
                    break;
                case 3:
                    printScore();
                    break;
                case 4:
                    searchScore();
                    break;
                case 5:
                    new Calculator();
                    break;
                case 6:
                    friendAddress();
                    break;
                case 7:
                    System.out.println("프로그램을 종료합니다.");
                    return;
                default:
                    System.out.println("1~7번 중에서 입력하세요.");
            }
        }
    }

    static void introduce() {
        System.out.println("안녕하세요! 저는 OO학번 OOO입니다.");
        System.out.println("전공은 컴공이며, 미래 직업은 OO를 목표로 열심히 공부하고 있습니다.");
    }

    static void inputScore() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("c:\\temp\\score.txt"));

            System.out.println("학생 이름과 이산구조과목의 중간성적(40), 기말성적(40), 과제성적(20)을 입력하세요.");

            for (int i = 0; i < 5; i++) {
                System.out.print(">> ");
                String line = sc.nextLine();

                String[] data = line.split(",");

                String name = data[0];
                int mid = Integer.parseInt(data[1]);
                int fin = Integer.parseInt(data[2]);
                int report = Integer.parseInt(data[3]);

                Student s = new Student(name, mid, fin, report);
                map.put(name, s);

                bw.write(s.toString());
                bw.newLine();
            }

            bw.close();
            System.out.println("총 5개의 학생성적을 읽었습니다.");

        } catch (Exception e) {
            System.out.println("입력 오류가 발생했습니다.");
        }
    }

    static void printScore() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("c:\\temp\\score.txt"));

            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                System.out.println("------------------------");
                System.out.println("이름: " + data[0]);
                System.out.println("중간: " + data[1]);
                System.out.println("기말: " + data[2]);
                System.out.println("과제: " + data[3]);
                
                double total = Double.parseDouble(data[4]);
                System.out.println("성적합계: " + data[4]);
                System.out.println("학점: " + data[5]);
                System.out.println("------------------------");
            }

            br.close();

        } catch (Exception e) {
            System.out.println("파일을 읽을 수 없습니다. 먼저 메뉴 2번을 실행하세요.");
        }
    }

    static void searchScore() {
        loadScoreFile();
        
        while(true) {
        System.out.println("검색할 이름을 입력하세요 ");
        System.out.print("이름>> ");
        String name = sc.nextLine();
        
        if(name.equals("그만")) {
        	break;
        }
        if (map.containsKey(name)) {
            Student s = map.get(name);

            System.out.print("중간성적: " + s.mid +", ");
            System.out.print("기말성적: " + s.fin +", ");
            System.out.print("과제성적: " + s.report +", ");
            System.out.println("학점: " + s.grade);
        } else {
            System.out.println("해당 학생이 없습니다.");
        }
    }
    }
    static void loadScoreFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("c:\\temp\\score.txt"));

            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                String name = data[0];
                int mid = Integer.parseInt(data[1]);
                int fin = Integer.parseInt(data[2]);
                int report = Integer.parseInt(data[3]);

                Student s = new Student(name, mid, fin, report);
                map.put(name, s);
            }

            br.close();

        } catch (Exception e) {
            System.out.println("저장된 성적 파일이 없습니다.");
        }
    }

    static void friendAddress() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("c:\\temp\\friend.txt"));

            for (int i = 0; i < 3; i++) {
            //    System.out.println((i + 1) + "번째 친구 정보 입력");

                System.out.print("이름 입력 ==> ");
                String name = sc.nextLine();

                System.out.print("나이 입력 ==> ");
                String age = sc.nextLine();

                System.out.print("전화번호 입력 ==> ");
                String phone = sc.nextLine();
                
                System.out.print("전공 입력 ==> ");
                String major = sc.nextLine();

                System.out.print("주소 입력 ==> ");
                String address = sc.nextLine();

                bw.write(name + "," + age + "," + phone + "," + major + "," + address);
                bw.newLine();
            }

            bw.close();

            BufferedReader br = new BufferedReader(new FileReader("c:\\temp\\friend.txt"));

            System.out.println("-------------------------------------------------");
            System.out.println("행번호\t이름\t나이\t전화번호\t주소");
            System.out.println("-------------------------------------------------");

            String line;
            int count = 1;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                System.out.println(count + ". " + data[0] + "\t" + data[1] + "\t" + data[2] + "\t" + data[3]+ "\t" + data[4]);
                count++;
            }

            br.close();

        } catch (Exception e) {
            System.out.println("친구 주소록 처리 중 오류가 발생했습니다.");
        }
    }
}
