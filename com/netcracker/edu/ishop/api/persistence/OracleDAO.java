package netcracker.edu.ishop.api.persistence;

import netcracker.edu.ishop.api.objects.*;
import netcracker.edu.ishop.utils.UniqueIDGenerator;
import netcracker.edu.ishop.utils.oracledatabase.ConnectionPool;
import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class OracleDAO extends DAO {

    private static OracleDAO INSTANCE = new OracleDAO();

    public static final Logger log = Logger.getLogger(DAOInMemoryJSON.class);

    //private  ConnectionPool connectionPool = ConnectionPool.getInstance();


    private OracleDAO() {
    }

    public static OracleDAO getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OracleDAO();
        }
        return INSTANCE;
    }

    @Override
    public <T extends AbstractBusinessObject> T create(Class<T> abObjType) {

        UniqueIDGenerator UIDGenerator = UniqueIDGenerator.getInstance();
        if (AbstractBusinessObject.class.isAssignableFrom(abObjType)) {
            return spawnBusinessObjectInstanceByReflection(abObjType, UIDGenerator.getID());
        }
        return null;
    }

    private <T extends AbstractBusinessObject> T spawnBusinessObjectInstanceByReflection(Class<T> abObjType, BigInteger newId) {
        T newBusinessObjectInstance = null;
        try {
            Constructor<T> constructor = abObjType.getConstructor(BigInteger.class);
            newBusinessObjectInstance = constructor.newInstance(newId);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException exceptionMessage) {
            log.info(exceptionMessage);
        }
        return newBusinessObjectInstance;
    }


    @Override
    public <T extends AbstractBusinessObject> T load() {
        return null;
    }


    @Override
    public <T extends AbstractBusinessObject> void save(T abObj) {

        if (abObj.getClass().isAssignableFrom(User.class)) {
            saveUser((User) abObj) ;
        }
        if (abObj.getClass().isAssignableFrom(Folder.class)) {
            saveFolder((Folder) abObj) ;
        }
        if (abObj.getClass().isAssignableFrom(ItemProperty.class)) {
            saveItemProperty((ItemProperty) abObj) ;
        }
        if (abObj.getClass().isAssignableFrom(Order.class)) {
            saveOrder((Order) abObj) ;
        }




    }

    private void saveUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User can't be null!");
        }
        Connection con = null;
        try {

            con = ConnectionPool.getInstance().getPooledConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO TUSER (ID, NAME, PASSWORD, GROUPTYPE, ORDER_ID) VALUES (?,?,?,?,?)");

            ps.setInt(1, user.getId().intValue());
            ps.setString(2, user.getName());
            ps.setString(3, Arrays.toString(user.getPassword()));
            ps.setString(4, user.getGroup().toString());
            ps.setInt(5, user.getOrderId().intValue());
            ps.executeUpdate();

        } catch (SQLException sql) {
            sql.printStackTrace();
        } finally {
            if (con != null) try {
                con.close();
            } catch (Exception ignore) {
            }
        }
    }

    private void saveFolder(Folder folder) {
        if (folder == null) {
            throw new IllegalArgumentException("Folder can't be null!");
        }
        Connection con = null;
        try {

            con = ConnectionPool.getInstance().getPooledConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO TFOLDER (ID, NAME, PARENT_ID) VALUES (?,?,?)");

            ps.setInt(1, folder.getId().intValue());
            ps.setString(2, folder.getName());
            ps.setInt(3, folder.getParentFolderId().intValue());

            ps.executeUpdate();

        } catch (SQLException sql) {
            sql.printStackTrace();
        } finally {
            if (con != null) try {
                con.close();
            } catch (Exception ignore) {
            }
        }
    }


    private void saveItemProperty(ItemProperty itemProperty) {
        if (itemProperty == null) {
            throw new IllegalArgumentException("itemProperty can't be null!");
        }
        Connection con = null;
        try {

            con = ConnectionPool.getInstance().getPooledConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO TItemProperty (ID, NAME, PROPERTYNAME) VALUES (?,?,?)");

            ps.setInt(1, itemProperty.getId().intValue());
            ps.setString(2, itemProperty.getName());
            ps.setString(3, itemProperty.toString());

            ps.executeUpdate();

        } catch (SQLException sql) {
            sql.printStackTrace();
        } finally {
            if (con != null) try {
                con.close();
            } catch (Exception ignore) {
            }
        }
    }

    private void saveOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order can't be null!");
        }
        Connection con = null;
        try {

            con = ConnectionPool.getInstance().getPooledConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO OrderTable (ID, NAME, ORDER_STATUS, ORDER_DATE, USER_ID) VALUES (?,?,?,?,?)");

            ps.setInt(1, order.getId().intValue());
            ps.setString(2, order.getName());
            ps.setString(3, order.getText());
            ps.setString(4, order.getOrderDate());
            ps.setInt(5, order.getUserId().intValue());

            ps.executeUpdate();

        } catch (SQLException sql) {
            sql.printStackTrace();
        } finally {
            if (con != null) try {
                con.close();
            } catch (Exception ignore) {
            }
        }
    }



        @Override
        public <T extends AbstractBusinessObject> void delete (T abObj){

        }

        @Override
        public <T extends AbstractBusinessObject> Map getDataMapByABOName (Class < T > abObj) {
            return null;
        }

        @Override
        public User findUserByName (String username){
            return null;
        }

        @Override
        public <T extends AbstractBusinessObject> T findAbstractBusinessObjByName (Class < T > cls, String username){
            return null;
        }

        @Override
        public Folder findFolderInstanceByName (String folderName){
            return null;
        }

        @Override
        public Folder findParentFoldersWithGivenParentId (BigInteger givenParentId){
            return null;
        }

        @Override
        public List<Folder> findAllFoldersWithGivenParentId (BigInteger givenParentId){
            return null;
        }

        @Override
        public List<Item> findItemsWithGivenFolderId (BigInteger givenFolderId){
            return null;
        }

        @Override
        public Item searchForItemObjectInGivenList (List < Item > itemList, String givenItemName){
            return null;
        }

        @Override
        public <T extends AbstractBusinessObject> T findABOInstanceById (Class < T > cls, BigInteger id){
            return null;
        }

        @Override
        public List<String> findOnlyItemsAndFoldersWithGivenParentId (BigInteger givenParentId){
            return null;
        }

        @Override
        public void DAOExit () {

        }
    }
