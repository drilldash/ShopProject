package netcracker.edu.ishop.api.persistence;

import netcracker.edu.ishop.api.commands.engine.CommandEngine;
import netcracker.edu.ishop.api.currentsession.CurrentSessionState;
import netcracker.edu.ishop.api.objects.*;
import netcracker.edu.ishop.utils.UniqueIDGenerator;
import netcracker.edu.ishop.utils.UserGroupTypes;
import netcracker.edu.ishop.utils.gson.SerializationUtils;
import netcracker.edu.ishop.utils.oracledatabase.ConnectionPool;
import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.nio.file.AccessDeniedException;
import java.sql.*;
import java.util.*;

@SuppressWarnings("ALL")
public class OracleDAO extends DAO {

    private static OracleDAO INSTANCE = new OracleDAO();

    private static final Logger log = Logger.getLogger(DAOInMemoryJSON.class);

    //private  ConnectionPool connectionPool = ConnectionPool.getInstance();

    private OracleDAO() {
    }

    public static OracleDAO getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OracleDAO();
        }
        return INSTANCE;
    }

////    CREATE

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
            createRecordInDatabase(abObjType, newId);

        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException exceptionMessage) {
            log.info(exceptionMessage);
        }
        return newBusinessObjectInstance;
    }

    private <T extends AbstractBusinessObject> void createRecordInDatabase(Class<T> abObjType, BigInteger newId) {

        String query = null;
        if (abObjType.isAssignableFrom(User.class)) {
            query = "INSERT INTO TUSER (ID) VALUES (?)";
        }
        if (abObjType.isAssignableFrom(Folder.class)) {
            query = "INSERT INTO TFOLDER (ID) VALUES (?)";
        }
        if (abObjType.isAssignableFrom(ItemProperty.class)) {
            query = "INSERT INTO TITEMPROPERTY (ID) VALUES (?)";
        }
        if (abObjType.isAssignableFrom(Order.class)) {
            query = "INSERT INTO TORDER (ID) VALUES (?)";
        }
        if (abObjType.isAssignableFrom(Item.class)) {
            query = "INSERT INTO TITEM (ID) VALUES (?)";
        }

        Connection con = null;
        try {

            con = ConnectionPool.getInstance().getPooledConnection();
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, newId.intValue());
            ps.executeQuery();

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
    public <T extends AbstractBusinessObject> T load() {
        return null;
    }

////    SAVE

    @Override
    public <T extends AbstractBusinessObject> void save(T abObj) {

        log.info(abObj.getClass());
        if (abObj.getClass().isAssignableFrom(User.class)) {
            saveUser((User) abObj);
        }
        if (abObj.getClass().isAssignableFrom(Folder.class)) {
            saveFolder((Folder) abObj);
        }
        if (abObj.getClass().isAssignableFrom(ItemProperty.class)) {
            saveItemProperty((ItemProperty) abObj);
        }
        if (abObj.getClass().isAssignableFrom(Order.class)) {
            saveOrder((Order) abObj);
            saveItemsInOrder((Order) abObj);
        }
        if (abObj.getClass().isAssignableFrom(Item.class)) {
            saveItem((Item) abObj);
            saveItemPropertyValues((Item) abObj);
        }
    }

    private void saveUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User can't be null!");
        }
        Connection con = null;
        try {

            con = ConnectionPool.getInstance().getPooledConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE TUSER SET NAME=?, PASSWORD=?, GROUPTYPE=?, ORDER_ID=? WHERE ID=?");
            ps.setString(1, user.getName());
            ps.setString(2, String.valueOf(user.getPassword()));
            ps.setString(3, user.getGroup().toString());

            if (user.getOrderId() != null) {
                ps.setInt(4, user.getOrderId().intValue());
            }
            else {
                ps.setNull(4, Types.NUMERIC);
            }

            ps.setInt(5, user.getId().intValue());
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
            PreparedStatement ps = con.prepareStatement("UPDATE TFOLDER SET NAME=?, PARENT_ID=? WHERE ID=?");

            ps.setString(1, folder.getName());
            if (folder.getParentFolderId() != null) {
                ps.setInt(2, folder.getParentFolderId().intValue());
            } else {
                ps.setNull(2, Types.NUMERIC);
            }

            ps.setInt(3, folder.getId().intValue());
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
            PreparedStatement ps = con.prepareStatement("UPDATE TItemProperty SET NAME=? WHERE ID=?");


            ps.setString(1, itemProperty.getName());
            ps.setInt(2, itemProperty.getId().intValue());

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
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE TORDER SET NAME=?, ORDER_STATUS=?, ORDER_DATE=?, USER_ID=? WHERE ID=?");


            ps.setString(1, order.getName());
            ps.setString(2, order.getOrderStatus());
            ps.setString(3, order.getOrderDate());
            ps.setInt(4, order.getUserId().intValue());
            ps.setInt(5, order.getId().intValue());

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


    private void saveItemsInOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order can't be null!");
        }



        List<BigInteger> idItemList = order.getItemListIds();

        Connection con = null;

        if (idItemList.size() > 0) {


//            String mergeQuery = "MERGE INTO TORDERHISTORY d " +
//                    "USING ( SELECT ? AS order_id, ? AS item_id FROM DUAL ) s " +
//                    "ON ( d.order_id = s.order_id and d.item_id = s.item_id )" +
//                    "WHEN NOT MATCHED THEN " +
//                    "INSERT ( d.order_id, d.item_id) VALUES ( s.order_id, s.item_id) " +
//                    "WHEN MATCHED THEN " +
//                    "NULL";

            String insertQuery = "INSERT INTO TORDERHISTORY (order_id, item_id) VALUES (?, ?)";

            con = ConnectionPool.getInstance().getPooledConnection();
            try (PreparedStatement ps = con.prepareStatement(insertQuery)) {

                //con.setAutoCommit(false);

                for (BigInteger itemId : idItemList) {

                    ps.setInt(1, order.getId().intValue());
                    ps.setInt(2, itemId.intValue());
                    ps.addBatch();
                }

                ps.executeBatch();
                //con.commit();

            } catch (SQLException sql) {
                sql.printStackTrace();
            } finally {
                if (con != null) try {
                    con.close();
                } catch (Exception ignore) {
                }
            }
        }
    }

    private void saveItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item can't be null!");
        }
        Connection con = null;
        try {

            con = ConnectionPool.getInstance().getPooledConnection();
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE TITEM SET NAME=?, FOLDER_ID=?, ITEMTYPE=? WHERE ID=?");
            ps.setString(1, item.getName());
            ps.setInt(2, item.getFolderId().intValue());
            ps.setString(3, null);
            ps.setInt(4, item.getId().intValue());

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

    private void saveItemPropertyValues(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item can't be null!");
        }


        List<ItemPropertyValue> itemPropVals = item.getCharsVals();

        if (itemPropVals.size() > 0) {

            for (ItemPropertyValue itemPropWithVal : itemPropVals) {
                ItemProperty itemProp = itemPropWithVal.getKey();
                saveItemProperty(itemProp);
            }

            Connection con = null;

            try {
                con = ConnectionPool.getInstance().getPooledConnection();

                String mergeQuery = "MERGE INTO TITEMPROPERTYVALUE d " +
                        "USING ( SELECT ? AS item_id, ? AS property_id FROM DUAL ) s " +
                        "ON ( d.item_id = s.item_id and d.property_id = s.property_id ) " +
                        "WHEN NOT MATCHED THEN " +
                        "INSERT ( d.item_id, d.property_id, d.propertyvalue ) VALUES ( s.item_id, s.property_id, ?) " +
                        " WHEN MATCHED THEN UPDATE SET d.propertyvalue = ?";

                log.info(mergeQuery);

                for (ItemPropertyValue itemPropWithVal : itemPropVals) {
                    ItemProperty itemProp = itemPropWithVal.getKey();
                    String itemPropValue = itemPropWithVal.getVal();

                    PreparedStatement ps = con.prepareStatement(mergeQuery);

                    ps.setInt(1, item.getId().intValue());
                    ps.setInt(2, itemProp.getId().intValue());
                    ps.setString(3, itemPropValue);
                    ps.setString(4, itemPropValue);

                    ps.execute();

                }


            } catch (SQLException sql) {
                sql.printStackTrace();
            } finally {
                if (con != null) try {
                    con.close();
                } catch (Exception ignore) {
                }
            }
        } else {
            log.info("No item properties with vals are available to save to database.");
        }
    }

    @Override
    public <T extends AbstractBusinessObject> void delete(T aboObj) {
        if (AbstractBusinessObject.class.isAssignableFrom(aboObj.getClass())) {
            deleteUser((User) aboObj);
        }

    }

    private void deleteUser(User user) {
        Connection con = null;
        try {

            con = ConnectionPool.getInstance().getPooledConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM TUSER WHERE ID=?");

            ps.setInt(1, user.getId().intValue());
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
    public <T extends AbstractBusinessObject> Map<BigInteger, T> getDataMapByABOName(Class<T> abObjType) {
        if (abObjType.isAssignableFrom(User.class)) {
            return loadMapUser();
        }
        if (abObjType.isAssignableFrom(Folder.class)) {
            return loadMapFolder();
        }
        if (abObjType.isAssignableFrom(ItemProperty.class)) {
            throw new IllegalAccessError("Not implemented yet");
        }
        if (abObjType.isAssignableFrom(Order.class)) {
            return loadMapOrder();
        }
        if (abObjType.isAssignableFrom(Item.class)) {
            return loadMapItem();
        }
        return null;
    }

    private Map loadMapUser() {
        Connection con = null;
        try {

            con = ConnectionPool.getInstance().getPooledConnection();
            Statement st = con.createStatement();
            st.executeQuery("SELECT ID, NAME FROM TUSER");


            try (ResultSet rs = st.getResultSet()) {
                Map<BigInteger, String> userMap = new HashMap<>();
                while (rs.next()) {
                    userMap.put(BigInteger.valueOf(rs.getInt(1)), rs.getString(2));
                }
                return userMap;
            }


        } catch (SQLException sql) {
            sql.printStackTrace();
        } finally {
            if (con != null) try {
                con.close();
            } catch (Exception ignore) {
            }
        }
        return null;
    }

    private Map loadMapFolder() {
        Connection con = null;
        try {

            con = ConnectionPool.getInstance().getPooledConnection();
            Statement st = con.createStatement();
            st.executeQuery("SELECT ID, NAME FROM TFOLDER");


            try (ResultSet rs = st.getResultSet()) {
                Map<BigInteger, String> folderMap = new HashMap<>();
                while (rs.next()) {
                    folderMap.put(BigInteger.valueOf(rs.getInt(1)), rs.getString(2));
                }
                return folderMap;
            }


        } catch (SQLException sql) {
            sql.printStackTrace();
        } finally {
            if (con != null) try {
                con.close();
            } catch (Exception ignore) {
            }
        }
        return null;
    }

    private Map loadMapItem() {
        Connection con = null;
        try {

            con = ConnectionPool.getInstance().getPooledConnection();
            Statement st = con.createStatement();
            st.executeQuery("SELECT ID, NAME FROM TITEM");


            try (ResultSet rs = st.getResultSet()) {
                Map<BigInteger, String> itemMap = new HashMap<>();
                while (rs.next()) {
                    itemMap.put(BigInteger.valueOf(rs.getInt(1)), rs.getString(2));
                }
                return itemMap;
            }


        } catch (SQLException sql) {
            sql.printStackTrace();
        } finally {
            if (con != null) try {
                con.close();
            } catch (Exception ignore) {
            }
        }
        return null;
    }

    private Map loadMapOrder() {
        Connection con = null;
        try {

            con = ConnectionPool.getInstance().getPooledConnection();
            Statement st = con.createStatement();
            st.executeQuery("SELECT ID, ORDER_STATUS, ORDER_DATE, USER_ID FROM TORDER");


            try (ResultSet rs = st.getResultSet()) {
                Map<BigInteger, Order> orderMap = new HashMap<>();
                while (rs.next()) {
                    Order mockOrder = new Order(BigInteger.valueOf(rs.getInt(1)));
                    mockOrder.setOrderStatus(rs.getString(2));
                    mockOrder.setOrderDate(rs.getString(3));
                    mockOrder.setUserId(BigInteger.valueOf(rs.getInt(4)));
                    //loading the list of item id's which were in order;
                    mockOrder.setItemList(loadItemsIdsForOrder(mockOrder));
                    orderMap.put(BigInteger.valueOf(rs.getInt(1)), mockOrder);
                }
                return orderMap;
            }


        } catch (SQLException sql) {
            sql.printStackTrace();
        } finally {
            if (con != null) try {
                con.close();
            } catch (Exception ignore) {
            }
        }
        return null;
    }

    private List<BigInteger> loadItemsIdsForOrder(Order order) {
        Connection con = null;
        try {

            con = ConnectionPool.getInstance().getPooledConnection();
            String sqlQuery = "SELECT ITEM_ID FROM TORDERHISTORY WHERE ORDER_ID=?";
            PreparedStatement ps = con.prepareStatement(sqlQuery);
            ps.setInt(1, order.getId().intValue());

            try (ResultSet rs = ps.executeQuery()) {

                if (rs != null) {
                    List<BigInteger> resultItemIdList = new ArrayList<>();
                    while (rs.next()) {
                        resultItemIdList.add(BigInteger.valueOf(rs.getInt(1)));


                    }
                    return resultItemIdList;
                }

            }


        } catch (SQLException sql) {
            sql.printStackTrace();
        } finally {
            if (con != null) try {
                con.close();
            } catch (Exception ignore) {
            }
        }
        return null;
    }











    @Override
    public User findUserByName(String username) {
        Connection con = null;
        try {

            con = ConnectionPool.getInstance().getPooledConnection();
            String sqlQuery = "SELECT ID, NAME, PASSWORD, GROUPTYPE, ORDER_ID FROM TUSER WHERE NAME=?";
            PreparedStatement ps = con.prepareStatement(sqlQuery);
            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs != null) {
                    if (rs.next()) {
                        User user = new User(BigInteger.valueOf(rs.getInt(1)));
                        user.setName(rs.getString(2));
                        user.setPassword(rs.getString(3).toCharArray());
                        user.setGroupType(UserGroupTypes.setUserGroupType(rs.getString(4)));

                        BigInteger orderId = rs.getObject("ORDER_ID") != null ? BigInteger.valueOf(rs.getInt("ORDER_ID")) : null;

                        user.setOrderId(orderId);
                        //log.info(user);
                        return user;
                    }
                }

            }


        } catch (SQLException sql) {
            sql.printStackTrace();
        } finally {
            if (con != null) try {
                con.close();
            } catch (Exception ignore) {
            }
        }
        return null;
    }

    @Override
    public Item findItemByName(String givenItemName) {
        Connection con = null;
        try {

            con = ConnectionPool.getInstance().getPooledConnection();
            String sqlQuery = "SELECT ID, NAME, FOLDER_ID FROM TITEM WHERE NAME=?";
            PreparedStatement ps = con.prepareStatement(sqlQuery);
            ps.setString(1, givenItemName);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs != null) {
                    if (rs.next()) {
                        Item item = new Item(BigInteger.valueOf(rs.getInt(1)));
                        item.setName(rs.getString(2));
                        item.setFolderId(BigInteger.valueOf(rs.getInt(3)));
                        return item;
                    }
                }

            }


        } catch (SQLException sql) {
            sql.printStackTrace();
        } finally {
            if (con != null) try {
                con.close();
            } catch (Exception ignore) {
            }
        }
        return null;
    }


    @Override
    public Folder findFolderInstanceByName(String folderName) {
        Connection con = null;
        try {

            con = ConnectionPool.getInstance().getPooledConnection();
            String sqlQuery = "SELECT ID, NAME, PARENT_ID FROM TFOLDER WHERE NAME = ?";
            PreparedStatement ps = con.prepareStatement(sqlQuery);
            ps.setString(1, folderName);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Folder folder = new Folder(BigInteger.valueOf(rs.getInt(1)));
                    folder.setName(rs.getString(2));

                    folder.setParentFolderId(BigInteger.valueOf(rs.getInt(3)));
                    return folder;
                }
            }


        } catch (SQLException sql) {
            sql.printStackTrace();
        } finally {
            if (con != null) try {
                con.close();
            } catch (Exception ignore) {
            }
        }
        return null;
    }

    @Override
    public ItemProperty findItemPropertyInstanceByName(String itemPropertyName) {
        Connection con = null;
        try {

            con = ConnectionPool.getInstance().getPooledConnection();
            String sqlQuery = "SELECT ID, NAME FROM TITEMPROPERTY WHERE NAME = ?";
            PreparedStatement ps = con.prepareStatement(sqlQuery);
            ps.setString(1, itemPropertyName);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ItemProperty itemProperty = new ItemProperty(BigInteger.valueOf(rs.getInt(1)));
                    itemProperty.setName(rs.getString(2));
                    return itemProperty;
                }
            }


        } catch (SQLException sql) {
            sql.printStackTrace();
        } finally {
            if (con != null) try {
                con.close();
            } catch (Exception ignore) {
            }
        }
        return null;
    }

    @Override
    public Folder findParentFoldersWithGivenParentId(BigInteger givenParentId) {
        Connection con = null;
        try {

            con = ConnectionPool.getInstance().getPooledConnection();
            String sqlQuery = "SELECT ID, NAME, PARENT_ID FROM TFOLDER WHERE ID = ?";
            PreparedStatement ps = con.prepareStatement(sqlQuery);
            ps.setInt(1, givenParentId.intValue());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Folder folder = new Folder(BigInteger.valueOf(rs.getInt(1)));
                    folder.setName(rs.getString(2));
                    folder.setParentFolderId(BigInteger.valueOf(rs.getInt(3)));
                    return folder;
                }
            }


        } catch (SQLException sql) {
            sql.printStackTrace();
        } finally {
            if (con != null) try {
                con.close();
            } catch (Exception ignore) {
            }
        }
        return null;
    }

    @Override
    public List<Folder> findAllFoldersWithGivenParentId(BigInteger givenParentId) {
        Connection con = null;
        try {

            con = ConnectionPool.getInstance().getPooledConnection();
            String sqlQuery = "SELECT ID, NAME, PARENT_ID FROM TFOLDER WHERE PARENT_ID = ?";
            PreparedStatement ps = con.prepareStatement(sqlQuery);
            ps.setInt(1, givenParentId.intValue());

            try (ResultSet rs = ps.executeQuery()) {
                List<Folder> folderList = new ArrayList<>();
                while (rs.next()) {
                    //rs.next() removed
                    Folder folder = new Folder(BigInteger.valueOf(rs.getInt(1)));
                    folder.setName(rs.getString(2));
                    folder.setParentFolderId(BigInteger.valueOf(rs.getInt(3)));
                    folderList.add(folder);
                }
                return folderList;
            }


        } catch (SQLException sql) {
            sql.printStackTrace();
        } finally {
            if (con != null) try {
                con.close();
            } catch (Exception ignore) {
            }
        }
        return null;
    }

    @Override
    public List<Item> findItemsWithGivenFolderId(BigInteger givenFolderId) {
        Connection con = null;
        try {
            con = ConnectionPool.getInstance().getPooledConnection();
            String sqlQuery = "SELECT ID, NAME, FOLDER_ID FROM TITEM WHERE FOLDER_ID = ?";
            PreparedStatement ps = con.prepareStatement(sqlQuery);
            ps.setInt(1, givenFolderId.intValue());

            try (ResultSet rs = ps.executeQuery()) {
                List<Item> itemList = new ArrayList<>();
                while (rs.next()) {
                    //rs.next() removed
                    Item item = new Item(BigInteger.valueOf(rs.getInt(1)));
                    item.setName(rs.getString(2));
                    item.setFolderId(BigInteger.valueOf(rs.getInt(3)));
                    itemList.add(item);
                }
                return itemList;
            }


        } catch (SQLException sql) {
            sql.printStackTrace();
        } finally {
            if (con != null) try {
                con.close();
            } catch (Exception ignore) {
            }
        }
        return null;
    }

    @Override
    public Item searchForItemObjectInGivenList(List<Item> itemList, String givenItemName) {
        for (Item item : itemList) {
            if (item.getName() != null && item.getName().contains(givenItemName)) {
                return item;
            }
            //something here
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends AbstractBusinessObject> T findABOInstanceById(Class<T> cls, BigInteger id) {

        if (cls.isAssignableFrom(User.class)) {
            return (T) findUserById(id);
        }
        if (cls.isAssignableFrom(Folder.class)) {
            //log.info("Querying DB for Folder...");
            return (T) findFolderById(id);
        }
        if (cls.isAssignableFrom(ItemProperty.class)) {
            throw new IllegalAccessError("Not implemented yet");
        }
        if (cls.isAssignableFrom(Order.class)) {
            return (T) findOrderById(id);
        }
        if (cls.isAssignableFrom(Item.class)) {
            return (T) findItemById(id);
        }
        return null;
    }

    private Item findItemById(BigInteger id) {
        Connection con = null;
        try {

            con = ConnectionPool.getInstance().getPooledConnection();
            String sqlQuery = "SELECT ID, NAME, FOLDER_ID FROM TITEM WHERE ID = ?";
            PreparedStatement ps = con.prepareStatement(sqlQuery);
            ps.setInt(1, id.intValue());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Item item = new Item(BigInteger.valueOf(rs.getInt(1)));
                    item.setName(rs.getString(2));
                    item.setFolderId(BigInteger.valueOf(rs.getInt(3)));
                    item.setCharsVals(loadItemPropertiesForItem(item));


                    return item;
                }
            }


        } catch (SQLException sql) {
            sql.printStackTrace();
        } finally {
            if (con != null) try {
                con.close();
            } catch (Exception ignore) {
            }
        }
        return null;
    }

    private List<ItemPropertyValue> loadItemPropertiesForItem(Item item) {
        Connection con = null;
        try {

            con = ConnectionPool.getInstance().getPooledConnection();
            String sqlQuery = "SELECT t1.NAME, t2.PROPERTY_ID, t2.PROPERTYVALUE " +
                    "FROM TITEMPROPERTY t1, TITEMPROPERTYVALUE t2 " +
                    "WHERE t1.id = t2.property_id and t2.item_id=?";
            PreparedStatement ps = con.prepareStatement(sqlQuery);
            ps.setInt(1, item.getId().intValue());

            List<ItemPropertyValue> itemPropertyValuesList = new ArrayList<>();

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BigInteger id = BigInteger.valueOf(rs.getInt(2));
                    ItemProperty itemProp = new ItemProperty(id);
                    itemProp.setName(rs.getString(1));
                    String value = rs.getString(3);

                    ItemPropertyValue itemPropVal = new ItemPropertyValue(itemProp, value);
                    itemPropertyValuesList.add(itemPropVal);

                }
                return itemPropertyValuesList;
            }


        } catch (SQLException sql) {
            sql.printStackTrace();
        } finally {
            if (con != null) try {
                con.close();
            } catch (Exception ignore) {
            }
        }
        return null;
    }



    private Order findOrderById(BigInteger id) {
        Connection con = null;
        try {

            con = ConnectionPool.getInstance().getPooledConnection();
            String sqlQuery = "SELECT ID, NAME, ORDER_STATUS, ORDER_DATE, USER_ID FROM TORDER WHERE ID=?";
            PreparedStatement ps = con.prepareStatement(sqlQuery);
            ps.setInt(1, id.intValue());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Order order = new Order(BigInteger.valueOf(rs.getInt(1)));
                    order.setName(rs.getString(2));
                    order.setOrderStatus(rs.getString(3));
                    order.setOrderDate(rs.getString(4));
                    order.setUserId(BigInteger.valueOf(rs.getInt(5)));
                    return order;
                }

            }


        } catch (SQLException sql) {
            sql.printStackTrace();
        } finally {
            if (con != null) try {
                con.close();
            } catch (Exception ignore) {
            }
        }
        return null;
    }

    private Folder findFolderById(BigInteger id) {
        Connection con = null;
        try {

            con = ConnectionPool.getInstance().getPooledConnection();
            String sqlQuery = "SELECT ID, NAME, PARENT_ID FROM TFOLDER WHERE ID=?";
            PreparedStatement ps = con.prepareStatement(sqlQuery);
            ps.setInt(1, id.intValue());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Folder folder = new Folder(BigInteger.valueOf(rs.getInt(1)));
                    folder.setName(rs.getString(2));

                    folder.setParentFolderId(BigInteger.valueOf(rs.getInt(3)));
                    log.info(folder);
                    return folder;
                }
            }


        } catch (SQLException sql) {
            sql.printStackTrace();
        } finally {
            if (con != null) try {
                con.close();
            } catch (Exception ignore) {
            }
        }
        return null;
    }

    private User findUserById(BigInteger id) {
        Connection con = null;
        try {

            con = ConnectionPool.getInstance().getPooledConnection();
            String sqlQuery = "SELECT ID, NAME, GROUPTYPE, ORDER_ID FROM TUSER WHERE ID = ?";
            PreparedStatement ps = con.prepareStatement(sqlQuery);
            ps.setInt(1, id.intValue());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User(BigInteger.valueOf(rs.getInt(1)));
                    user.setName(rs.getString(2));
                    user.setGroupType(UserGroupTypes.setUserGroupType(rs.getString(3)));
                    user.setOrderId(BigInteger.valueOf(rs.getInt(4)));
                    return user;
                }
            }


        } catch (SQLException sql) {
            sql.printStackTrace();
        } finally {
            if (con != null) try {
                con.close();
            } catch (Exception ignore) {
            }
        }
        return null;
    }

    @Override
    public List<String> findOnlyItemsAndFoldersWithGivenParentId(BigInteger givenParentId) {
        List<Folder> folderList = findAllFoldersWithGivenParentId(givenParentId);
        List<Item> itemList = findItemsWithGivenFolderId(givenParentId);

        List<String> resultList = new ArrayList<>();

        for (Folder folder : folderList) {
            resultList.add("F:" + folder.getName());
        }

        for (Item item : itemList) {
            resultList.add("I:" + item.getName());
        }

        return resultList;
    }

    @Override
    public void DAOExit() {
        CommandEngine comEngine = CommandEngine.getInstance();

        for (Iterator<BigInteger> bigIntIterator = CurrentSessionState.getCurrentSession().getAllSignedInUsers().iterator(); bigIntIterator.hasNext(); ) {
            BigInteger bigIntId = bigIntIterator.next();
            try {
                comEngine.executeCommand("sign_out");
            } catch (AccessDeniedException ADE) {
                log.info(ADE);
            }


        }
        SerializationUtils.saveLastID();

    }
}
