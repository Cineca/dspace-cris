package it.cilea.hku.authority.batch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;
import org.dspace.core.ConfigurationManager;

public class ScriptFixPositionSubPropertyCombo
{

    /** log4j logger */
    private static Logger log = Logger
            .getLogger(ScriptFixPositionSubPropertyCombo.class);

    private static Connection conn = null;

    private static final String template_query1 = "select parentproperty_id, typo_id from model_rp_jdyna_prop where parentproperty_id is not null group by typo_id, parentproperty_id, position having count(*) > 1";

    private static final String template_query2 = "select * from  model_rp_jdyna_prop where typo_id = # and parentproperty_id = # order by parentproperty_id, position";

    private static final String template_query3 = "update model_rp_jdyna_prop set position = # where id = #";

    private static boolean commit = true;

    public static Connection getConnection() throws SQLException
    {
        if (conn == null)
        {
            Properties connectionProps = new Properties();
            connectionProps.put("user",
                    ConfigurationManager.getProperty("db.username"));
            connectionProps.put("password",
                    ConfigurationManager.getProperty("db.password"));

            conn = DriverManager
                    .getConnection(ConfigurationManager.getProperty("db.url"),
                            connectionProps);

            System.out.println("Connected to database");
        }
        return conn;
    }

    public static void main(String[] args) throws ParseException, SQLException
    {

        try
        {
            Statement stmt = getConnection().createStatement();

            System.out.println("- Shoot required SQL ");
            System.out.print("- 1 ");
            System.out.println(template_query1);
            ResultSet rs = stmt.executeQuery(template_query1);
            while (rs.next())
            {
                Integer parentProperty_id = rs.getInt("parentproperty_id");
                Integer typo_id = rs.getInt("typo_id");

                String query2 = template_query2;
                query2 = query2.replaceFirst("#", "" + typo_id);
                query2 = query2.replaceFirst("#", "" + parentProperty_id);

                stmt = getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rsValue = stmt.executeQuery(query2);

                List<String> positions = new ArrayList<String>();
                Integer remembered = null;
                int i = 0;
                internal : while (rsValue.next())
                {
                    String position = rsValue.getString("position");
                    
                    System.out.print("Search match...");
                    if (positions.contains(position))
                    {
                        System.out.println("Founded... position replicated = "
                                + position);
                        Integer property_id = rsValue.getInt("id");
                        
                        rsValue.last();
                        String query3 = template_query3;
                        query3 = query3.replaceFirst("#", "" + ((remembered!=null)?remembered:(rsValue.getInt("position") + 1)));
                        query3 = query3.replaceFirst("#", "" + property_id);
                        System.out.println(query3);
                        if (commit)
                        {
                            stmt.executeUpdate(query3);
                        }
                        break internal;
                    }
                    else {
                        System.out.println(" " + position +" not found");
                        if (i!=Integer.parseInt(position)) {
                            remembered = i;
                            System.out.print(" - found hole - next insert at position: " + remembered);
                        }
                        i++;
                    }
                    positions.add(position);
                }
            }

        }
        catch (Exception ex)
        {
            log.error(ex.getMessage(), ex);
        }
        finally
        {
            getConnection().close();
        }

    }
}
