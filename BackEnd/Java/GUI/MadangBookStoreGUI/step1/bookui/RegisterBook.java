package step1.bookui;

import step1.entity.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class RegisterBook extends JFrame {
    private JTextField id, name, publisher, price;
    private JLabel lblId, lblName, lblPublisher, lblPrice;
    private JButton registerButton;
    private JButton resetButton;
    private JTextArea jTextArea;
    private ArrayList<Book> bookList;

    public RegisterBook() throws HeadlessException {
        // 윈도우 화면구성 설정
        setTitle("마당서점 도서 등록");
        setSize(300, 300);
        setLocationRelativeTo(null);// 화면 중앙에 윈도우 배치
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 윈도우 프레임 배치관리자 설정
        //setLayout(new BorderLayout());
        
        
        // 컴포넌트 생성
        id = new JTextField(10);
        id.setText("아이디 입력...");

        name = new JTextField(10);
        publisher = new JTextField(10);
        price = new JTextField(10);
        jTextArea = new JTextArea(4,20);
        bookList = new ArrayList<>();
        lblId = new JLabel("책아이디 :");
        lblName = new JLabel("책이름 :");
        lblPublisher = new JLabel("출판사 :");
        lblPrice = new JLabel("책가격 :");

        registerButton = new JButton("책등록");
        resetButton = new JButton("초기화");
        // 컴포넌트 이벤트 등록 및 구현
        // 입력 패널
        JPanel inputPannel = new JPanel();
        inputPannel.setLayout(new GridLayout(0,2));

        // 컴포넌트 배치/컴포넌트를 컨테이너에 등록
        inputPannel.add(lblId); inputPannel.add(id);
        inputPannel.add(lblName); inputPannel.add(name);
        inputPannel.add(lblPublisher); inputPannel.add(publisher);
        inputPannel.add(lblPrice); inputPannel.add(price);
        inputPannel.add(registerButton);
        inputPannel.add(resetButton);
        //inputPannel.setBackground(Color.BLUE);

        this.add(inputPannel);
        this.add(jTextArea,BorderLayout.SOUTH);


        // 키보드 엔터키로 등록 처리
        KeyAdapter enterKey = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    registerBook();
                }
            }
        };
        registerButton.addKeyListener(enterKey);
        price.addKeyListener(enterKey);
        id.addKeyListener(enterKey);
        name.addKeyListener(enterKey);
        publisher.addKeyListener(enterKey);


        // 화면 지우기
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                id.setText("");
                name.setText("");
                publisher.setText("");
                price.setText("");
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerBook();
            }
        });



        // 컨테이너 활성화(화면에 보이게)
        setVisible(true);
    }

    private void registerBook() {
        String id_ = id.getText();
        String name_ = name.getText();
        String publisher_ = publisher.getText();
        String price_ = price.getText();

        if( id_.length() < 3 || id_.isEmpty()){
            JOptionPane.showMessageDialog(null,"아이디를 입력하세요.");
            return;
        }

        Book book = new Book(Integer.parseInt(id_),
                name_,
                publisher_,
                Integer.parseInt(price_)
        );
        bookList.add(book);

        jTextArea.append(book.toString()+"\n");
        for (Book book1 : bookList){
            System.out.println(book1);
        }
    }

    public static void main(String[] args) {
        new RegisterBook();
    }
}
