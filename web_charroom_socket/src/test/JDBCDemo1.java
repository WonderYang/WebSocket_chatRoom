import org.junit.Test;

import java.sql.*;
import java.util.Collection;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 10:54 2019/7/27
 */
public class JDBCDemo1 {
    @Test
    public void test() throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/java7";
        String username = "root";
        String password = "yangyun199969";
        String sql = " insert into exam_result(name,yuwen,shuxue,yingyu) values(?,?,80,?);";
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            for(int i=0; i<2; i++) {
                preparedStatement.setString(1,"吕布"+i+"号");
                preparedStatement.setInt(2,70);
                preparedStatement.setInt(3,90);
                //能分辨SQL语句类型就最好不要使用execute()方法
                preparedStatement.executeUpdate();
            }
        }
    }
}
