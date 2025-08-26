package dao;

import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {

    private static final String URL  =
            "jdbc:mysql://localhost:3306/shopdb?useSSL=false&serverTimezone=Asia/Taipei&characterEncoding=utf8";
    private static final String USER = "root";
    private static final String PASS = "springboot";

    public List<Product> searchByName(String keyword) {
        String sql = """
                SELECT id, name, price, stock
                FROM product
                WHERE name LIKE ?
                ORDER BY id DESC
                """;
        List<Product> list = new ArrayList<>();

        try {
            // ★ 關鍵：顯式載入 MySQL 驅動，若拋 CNFE = classpath 沒驅動
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, "%" + (keyword == null ? "" : keyword.trim()) + "%");
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        list.add(new Product(
                                rs.getLong("id"),
                                rs.getString("name"),
                                rs.getDouble("price"),
                                rs.getInt("stock")
                        ));
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL Driver 未載入（請確認 WEB-INF/lib 有 mysql-connector-j）", e);
        } catch (SQLException e) {
            throw new RuntimeException("查詢失敗", e);
        }
        return list;
    }

    // 可選：在 IDE 直接執行這個 main 先驗證連線是否成功
    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection c = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/shopdb?useSSL=false&serverTimezone=Asia/Taipei&characterEncoding=utf8",
                "root", "springboot")) {
            System.out.println("OK, connected: " + (c != null));
        }
    }
}
