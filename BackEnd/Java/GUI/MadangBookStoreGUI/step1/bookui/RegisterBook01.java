package step1.bookui;

import step1.entity.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RegisterBook01 extends JFrame {
    private JTextField id, name, publisher, price;
    private JLabel lblId, lblName, lblPublisher, lblPrice;
    private JButton registerButton;
    private ArrayList<Book> bookList;

    public RegisterBook01() throws HeadlessException {
        // 윈도우 화면구성 설정
        setTitle("마당서점 도서 등록");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 윈도우 프레임 배치관리자 설정
        setLayout(new FlowLayout());

        // 컴포넌트 생성
        id = new JTextField(10);
        id.setText("아이디 입력...");
        name = new JTextField(10);
        publisher = new JTextField(10);
        price = new JTextField(10);
        bookList = new ArrayList<>();
        lblId = new JLabel("책아이디 :");
        lblName = new JLabel("책이름 :");
        lblPublisher = new JLabel("출판사 :");
        lblPrice = new JLabel("책가격 :");

        registerButton = new JButton("책등록");
        // 컴포넌트 이벤트 등록 및 구현

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id_ = id.getText();
                String name_ = name.getText();
                String publisher_ = publisher.getText();
                String price_ = price.getText();

                Book book = new Book(Integer.parseInt(id_),
                        name_,
                        publisher_,
                        Integer.parseInt(price_)
                        );
                bookList.add(book);

                for (Book book1 : bookList){
                    System.out.println(book1);
                }
            }
        });


        // 컴포넌트 배치/컴포넌트를 컨테이너에 등록
        add(lblId); add(id);
        add(lblName); add(name);
        add(lblPublisher); add(publisher);
        add(lblPrice); add(price);
        add(registerButton);

        // 컨테이너 활성화(화면에 보이게)
        setVisible(true);
    }

    public static void main(String[] args) {
        new RegisterBook01();
    }
}
