package step1.bookui;

import step1.entity.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Locale;

public class BookManagerUI extends JFrame implements ActionListener {
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

        // 도서 검색
        btnSearch.addActionListener(e->{
            String serchKeyword = tfSearch.getText().trim().toLowerCase();
            tableModel.setRowCount(0);
            for (Book book : bookList){
                if(book.getName().toLowerCase().contains(serchKeyword)){
                    tableModel.addRow(new Object[]{book.getId(),
                            book.getName(),
                            book.getPublisher(),
                            book.getPrice()
                    });
                }
            }
        });

        tableModel.addTableModelListener(e->{
            int row = e.getFirstRow();
            int col = e.getColumn();
            if(row >= 0 && col >= 0){
                int id = (int) tableModel.getValueAt(row,0);
                // 현재 테이블에서 선택한 행의 책이름, 출판사, 가격으로 수정
                for (Book book : bookList){
                    if(book.getId() == id){
                        book.setName((String) tableModel.getValueAt(row,1));
                        book.setPublisher((String) tableModel.getValueAt(row,2));
                        book.setPrice(Integer.parseInt(tableModel.getValueAt(row,3).toString()));
                        System.out.println(book);
                        break;
                    }
                }
            }
        });

        // 도서 삭제
        btnDelete.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            int row = bookTable.getSelectedRow();

            // 선택한 행이 없으면
            if(row == -1){
                JOptionPane.showMessageDialog(this,
                        "삭제할 행을 선택하세요.");
                return;
            }

            // 선택한 행이 있으면
            int confirm = JOptionPane.showConfirmDialog(this,
                    "정말 삭제하시겠습니까?", "확인",
                    JOptionPane.YES_NO_OPTION);

            if(confirm == JOptionPane.YES_NO_OPTION){
                int id = (int)tableModel.getValueAt(row,0);
                bookList.removeIf(book -> book.getId() == id);
                //도서 저장소(내부 자료구조)에서 삭제
//                for (Book book : bookList){
//                    if(book.getId() == id){
//                        bookList.remove(id-1);
//                        //bookList.remove(book);
//                        break;
//                    }
//                }

                // 테이블 화면에서 삭제
                tableModel.removeRow(row);

            }
    }

    public static void main(String[] args) {
        new BookManagerUI();
    }
}
