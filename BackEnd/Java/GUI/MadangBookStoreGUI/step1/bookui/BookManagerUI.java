package step1.bookui;

import step1.entity.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class BookManagerUI extends JFrame {
    private JTextField tfName, tfPublisher, tfPrice, tfSearch;
    private JButton btnAdd, btnDelete, btnSearch;
    private JTable bookTable;
    private DefaultTableModel tableModel;
    private ArrayList<Book> bookList = new ArrayList<>();
    private static int bookIdCounter=0;

    public BookManagerUI() throws HeadlessException {
        // 윈도우 화면구성 설정
        setTitle("\uD83D\uDCDA 마당서점 도서 관리자");
        setSize(800, 800);
        setLocationRelativeTo(null);// 화면 중앙에 윈도우 배치
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 상단 등록 및 검색 패널
        JPanel topPanel = new JPanel(new GridLayout(0,1));

        // 책 등록 컴포넌트 생성
        JPanel inputPanel = new JPanel();
        tfName = new JTextField(10);
        tfPublisher = new JTextField(10);
        tfPrice = new JTextField(10);
        btnAdd = new JButton("등록");
        inputPanel.setBackground(Color.lightGray);

        // 책 등록 패널
        inputPanel.add(new JLabel("책 이름 :")); inputPanel.add(tfName);
        inputPanel.add(new JLabel("출판사 :")); inputPanel.add(tfPublisher);
        inputPanel.add(new JLabel("책 가격 :")); inputPanel.add(tfPrice);
        inputPanel.add(btnAdd);

        // 검색 패널
        JPanel searchPanel = new JPanel();
        tfSearch = new JTextField(15);
        btnSearch = new JButton("검색");
        searchPanel.add(new JLabel("도서 이름 검색"));
        searchPanel.add(tfSearch);
        searchPanel.add(btnSearch);

        // 테이블 UI 생성 및 배치
        String[] colmnNames = {"아이디","책 이름","출판사","가격"};
        tableModel = new DefaultTableModel(colmnNames, 0);
        bookTable = new JTable(tableModel);
        bookTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(bookTable);

        // 삭제버튼
        btnDelete = new JButton("선택 삭제");

        // 패널 최종 등록 및 배치
        topPanel.add(inputPanel);
        topPanel.add(searchPanel);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(btnDelete, BorderLayout.SOUTH);

        // 도서 등록 기능
        btnAdd.addActionListener(e -> {
            String name = tfName.getText().trim();
            String publisher = tfPublisher.getText().trim();
            String price_ = tfPrice.getText().trim();

            if(name.isEmpty() || publisher.isEmpty() || price_.isEmpty()){
                JOptionPane.showMessageDialog(this,"모든 값을 입력해주세요.");
                return;
            }

            int price;
            try {
                price = Integer.parseInt(price_);
            }catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(this,"가격은 숫자로 입력하세요.");
                return;
            }

            Book book = new Book(++bookIdCounter, name, publisher, price);
            bookList.add(book);

            // 등록한 도서를 테이블에 출력
            tableModel.addRow(new Object[]{book.getId(),
                    book.getName(),
                    book.getPublisher(),
                    book.getPrice()
            });


        });


        setVisible(true);
    }

    public static void main(String[] args) {
        new BookManagerUI();
    }
}
