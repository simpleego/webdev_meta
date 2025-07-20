package step02.bookui;

import step01.entity.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class ReisterBook extends JFrame {
    private JTextField idField, nameField, publisherField, priceField;
    private DefaultListModel<String> bookListModel;
    private java.util.List<Book> bookData;

    public ReisterBook() {
        setTitle("마당서점 - 도서 정보 등록");
        setSize(400, 300);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 화면 중앙에 배치

        bookData = new ArrayList<>();
        bookListModel = new DefaultListModel<>();

        // 입력 패널
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("도서번호:"));
        idField = new JTextField();
        inputPanel.add(idField);

        inputPanel.add(new JLabel("도서명:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("출판사:"));
        publisherField = new JTextField();
        inputPanel.add(publisherField);

        inputPanel.add(new JLabel("가격:"));
        priceField = new JTextField();
        inputPanel.add(priceField);

        // 버튼 패널
        JPanel buttonPanel = new JPanel();
        JButton registerBtn = new JButton("등록하기");

        // 내부에 추가할 버튼과 메서드

        JButton viewListBtn = new JButton("도서목록 보기");
        viewListBtn.addActionListener(e -> showBookList());

        buttonPanel.add(registerBtn);
        buttonPanel.add(viewListBtn);


        // 📥 엔터 키 이벤트 등록
        ActionListener registerAction = e -> registerBook();
        registerBtn.addActionListener(registerAction);

        KeyAdapter enterKey = new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    registerBook();
            }
        };

        idField.addKeyListener(enterKey);
        nameField.addKeyListener(enterKey);
        publisherField.addKeyListener(enterKey);
        priceField.addKeyListener(enterKey);

        buttonPanel.add(registerBtn);

        // 도서 리스트 출력
        JList<String> bookList = new JList<>(bookListModel);
        JScrollPane listPane = new JScrollPane(bookList);
        listPane.setBorder(BorderFactory.createTitledBorder("등록된 도서"));

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(listPane, BorderLayout.SOUTH);

        setVisible(true);
    }

    // 📌 도서 등록 메서드
    private void registerBook() {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText().trim();
            String pub = publisherField.getText().trim();
            int price = Integer.parseInt(priceField.getText());

            if (name.isEmpty() || pub.isEmpty()) {
                JOptionPane.showMessageDialog(this, "모든 필드를 입력해주세요!");
                return;
            }

            Book book = new Book(id, name, pub, price);
            bookData.add(book);
            bookListModel.addElement(book.toString());

            // 입력 초기화
            idField.setText("");
            nameField.setText("");
            publisherField.setText("");
            priceField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "숫자 형식이 잘못되었습니다.");
        }
    }

    // 도서목록 조회
    private void showBookList() {
        JFrame listFrame = new JFrame("도서목록");
        listFrame.setSize(350, 300);
        listFrame.setLocationRelativeTo(this);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        if (bookData.isEmpty()) {
            textArea.setText("등록된 도서가 없습니다.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Book book : bookData) {
                sb.append(book.toString()).append("\n");
            }
            textArea.setText(sb.toString());
        }

        JScrollPane scrollPane = new JScrollPane(textArea);
        listFrame.add(scrollPane);
        listFrame.setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(ReisterBook::new);
    }
}
