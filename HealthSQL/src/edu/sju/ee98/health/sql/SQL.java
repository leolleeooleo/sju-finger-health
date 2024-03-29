/*
 * Copyright (c) 2012, St. John's University and/or its affiliates. All rights reserved.
 */
package edu.sju.ee98.health.sql;

import edu.sju.ee98.sql.SQLConnector;
import edu.sju.ee98.sql.Table;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 資料庫
 *
 * @author 98405067
 */
public class SQL extends SQLConnector {

    /**
     * 建立資料庫
     *
     * @param host 主機位置
     * @param name 資料庫名稱
     * @param user 使用者名稱
     * @param passwd 密碼
     */
    public SQL(String host, String name, String user, String passwd) {
        super(host, name + "?useUnicode=true&characterEncoding=utf8", user, passwd);
    }

    /**
     * 建立資料表
     *
     */
    public void createTables() {
        try {
            this.createTable(new User());
            this.createTable(new Group());
            this.createTable(new Register());
            this.createTable(new Miles());
            this.createTable(new Record());
            this.createTable(new Cost());
            this.createTable(new Fingerprint());
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 刪除資料表
     *
     */
    public void dropTables() {
        try {
            this.dropTable(new User());
            this.dropTable(new Group());
            this.dropTable(new Register());
            this.dropTable(new Miles());
            this.dropTable(new Record());
            this.dropTable(new Cost());
            this.dropTable(new Fingerprint());
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//Table User++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    /**
     * 建立使用者
     *
     * @param uid 使用者編號
     * @param account 帳號
     * @param password 密碼
     * @param group 群組編號
     * @param last_name 姓
     * @param fist_name 名
     * @param birthday 生日
     * @param address 地址
     * @param email 信箱
     * @param phone 電話
     * @return 使用者資料
     */
    public ArrayList<Table> createUser(String uid, String account, String password, int group, String last_name,
            String fist_name, Date birthday, String address, String email, String phone) {
        User user = new User(uid, account, password, group, last_name, fist_name, birthday, address, email, phone, 0);
        try {
            this.insert(user);
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.select(user, user.objectUID());
    }

    /**
     * 更新使用者資料
     *
     * @param uid 使用者編號
     * @param account 帳號
     * @param password 密碼
     * @param group 群組
     * @param last_name 姓氏
     * @param fist_name 名字
     * @param birthday 生日
     * @param address 地址
     * @param email 郵件
     * @param phone 電話
     * @return 使用者列表
     */
    public ArrayList<Table> updateUser(String uid, String account, String password, int group, String last_name,
            String fist_name, Date birthday, String address, String email, String phone) {
        User user = new User(uid, account, password, group, last_name, fist_name, birthday, address, email, phone, 0);
        try {
            this.update(user, user.objectACCOUNT(), user.objectPASSWORD(), user.objectGROUP(),
                    user.objectLAST_NAME(), user.objectFIRST_NAME(), user.objectBIRTHDAY(),
                    user.objectADDRESS(), user.objectEmail(), user.objectPHONE());
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.select(user, user.objectUID());
    }

    /**
     * 刪除使用者
     *
     * @param user 被刪除的使用者
     * @throws Exception 例外
     */
    public void deleteUser(User user) throws SQLException {
        this.delete(user);
    }

    /**
     * 登入使用者
     *
     * @param account 帳號
     * @param password 密碼
     * @return　使用者列表
     */
    public ArrayList<Table> logInUser(String account, String password) {
        User u = new User();
        u.setACCOUNT(account);
        u.setPASSWORD(password);
        return this.select(u, u.objectACCOUNT(), u.objectPASSWORD());
    }

    /**
     * 登入使用者
     *
     * @param fingerprint 指紋特徵值
     * @return 使用者列表
     */
    public ArrayList<Table> logInUser(byte[] fingerprint) {
        Fingerprint f = new Fingerprint();
        f.setFINGERPRINT(fingerprint);
        ArrayList<Table> select = this.select(f, Arrays.copyOfRange(f.sqlObject, 1, 196));
        if (select.size() > 0) {
            f = (Fingerprint) select.get(0);
            return this.select(new User(), f.objectUID());
        }
        return select;
    }

    /**
     * 登入登入站
     *
     * @param account 帳號
     * @param password 密碼
     * @return 登入站列表
     */
    public ArrayList<Table> logInRegister(String account, String password) {
        Register r = new Register();
        r.setACCOUNT(account);
        r.setPASSWORD(password);
        return this.select(r, r.objectACCOUNT(), r.objectPASSWORD());
    }

    /**
     * 使用者列表
     *
     * @return 使用者列表
     */
    public ArrayList<Table> listUser() {
        return this.select(new User());
    }

    /**
     * 取得用戶
     *
     * @param uid　用戶編號
     * @return　用戶
     */
    public User getUser(String uid) {
        User user = new User();
        user.setUID(uid);
        ArrayList<Table> select = this.select(user, user.objectUID());
        if (select.size() > 0) {
            return (User) select.get(0);
        }
        return null;
    }

    /**
     * 建立群組
     *
     * @param gid 群組標號
     * @param name 名稱
     * @return 群組
     */
    public Group createGroup(int gid, String name) {
        Group group = new Group(gid, name);
        try {
            this.insert(group);
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (Group) this.select(group, group.objectGID()).get(0);
    }

    /**
     * 修改群組
     *
     * @param table 群組
     * @param gid 群組編號
     * @param name 名稱
     * @return 群組
     */
    public Group modifyGroup(Group table, String gid, String name) {
        return null;
    }

    /**
     * 刪除群組
     *
     * @param group 群組
     * @throws SQLException 例外
     */
    public void deleteGroup(Group group) throws SQLException {
        this.delete(group);
    }

    /**
     * 建立登錄站
     *
     * @param rid 登錄站編號
     * @param account 帳號
     * @param password 密碼
     * @param region 地區
     * @param name 名稱
     * @return 登錄站列表
     */
    public ArrayList<Table> createRegister(int rid, String account, String password, String region, String name) {
        Register register = new Register(rid, account, password, region, name);
        try {
            this.insert(register);
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.select(register, register.objectRID());
    }

    /**
     * 修改登錄站
     *
     * @param root 管理員
     * @param table 登錄站
     * @param account 帳號
     * @param password 密碼
     * @param region 地區
     * @param rid 登錄站編號
     * @param name 名稱
     * @return 登錄站
     */
    public Register updateRegister(int rid, String account, String password, String region, String name) {
        try {
            Register register = new Register(rid, account, password, region, name);
            this.update(register, register.objectACCOUNT(), register.objectPASSWORD(),
                    register.objectREGION(), register.objectNAME());
            return this.getRegister(rid);
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Register getRegister(int rid) {
        Register reg = new Register(rid);
        ArrayList<Table> select = this.select(reg, reg.objectRID());
        if (select.size() > 0) {
            return (Register) select.get(0);
        }
        return null;
    }

    /**
     * 刪除登錄站
     *
     * @param register 登錄站
     * @throws SQLException 資料庫例外
     */
    public void deleteRegister(Register register) throws SQLException {
        this.delete(register);
    }

    /**
     * 登入站列表
     *
     * @return 登入站列表
     */
    public ArrayList<Table> listRegister() {
        return this.select(new Register());
    }

    /**
     * 建立里程
     *
     * @param register_a 登錄站A
     * @param register_b 登錄站B
     * @param meter 公尺
     * @return 里程列表
     */
    public ArrayList<Table> createMiles(int register_a, int register_b, int meter) {
        Miles miles = new Miles(register_a, register_b, meter);
        try {
            this.insert(miles);
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.select(miles, miles.objectMETER());
    }

    /**
     * 修改里程
     *
     * @param mile 里程
     * @return 里程列表
     */
    public ArrayList<Table> updateMiles(Miles mile) {
        try {
            this.update(mile, mile.objectREGISTER_A(), mile.objectREGISTER_B(), mile.objectMETER());
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.select(mile, mile.objectREGISTER_A(), mile.objectREGISTER_B());
    }

    /**
     * 刪除里程
     *
     * @param miles 里程
     * @throws SQLException 資料庫例外
     */
    public void deleteMiles(Miles miles) throws SQLException {
        this.delete(miles);
    }

    /**
     * 取得里程
     *
     * @param ridA 登錄站編號
     * @param ridB 登錄站編號
     * @return 里程
     */
    public Miles getMiles(int ridA, int ridB) {
        Miles miles = new Miles(ridA, ridB);
        ArrayList<Table> select = this.select(miles, miles.objectREGISTER_A(), miles.objectREGISTER_B());
        if (select.size() > 0) {
            return (Miles) select.get(0);
        }
        return null;
    }

    /**
     * 里程列表
     *
     * @return 里程列表
     */
    public ArrayList<Table> listMiles() {
        return this.select(new Miles());
    }

    /**
     * 建立紀錄
     *
     * @param root 管理員
     * @param time 時間
     * @param user 使用者
     * @param register 登錄站
     * @return
     */
    public Record createRecord(java.util.Date time, String user, int register) {
        Record record = new Record(time, user, register);
        try {
            this.insert(record);
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (record);

    }

    /**
     * 修改紀錄
     *
     * @param record
     * @param user 使用者
     * @param register 登錄站
     * @return
     */
    public Record modifyRecord(Record record, int user, int register) {
        return null;
    }

    /**
     * 刪除紀錄
     *
     * @param record
     * @throws SQLException
     */
    public void deleteRecord(Record record) throws SQLException {
        this.delete(record);
    }

    /**
     *
     * @return
     */
    public ArrayList<Table> listRecord() {
        return this.select(new Record());
    }

//Table Cost+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    /**
     * 建立消費
     *
     * @param root
     * @param time 時間
     * @param store 商店
     * @param user 使用者
     * @param points 點數
     * @return
     */
    public Cost createCost(java.util.Date time, String store, String user, int points) {
        User u = new User();
        u.setUID(user);
        int point = this.plusPoints(u) - this.costPoints(u);
        if (point < points) {
            Logger.getLogger(SQL.class.getName()).log(Level.WARNING, "point=" + point + ",need=" + points, "not enough");
            return null;
        }
        Cost cost = new Cost(time, store, user, points);
        try {
            this.insert(cost);
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (cost);
    }

    /**
     * 修改消費
     *
     * @param table 消費
     * @param store 商店
     * @param user 使用者
     * @param points 點數
     * @return
     */
    public Cost modifyCost(Cost table, String store, String user, int points) {
        return null;
    }

    /**
     * 刪除消費
     *
     * @param cost
     * @throws SQLException
     */
    public void deleteCost(Cost cost) throws SQLException {
        this.delete(cost);
    }

    /**
     *
     * @return
     */
    public ArrayList<Table> listCost() {
        return this.select(new Cost());
    }

//Table finger+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public ArrayList<Table> createFingerprint(String uid, byte[] fp) {
        Fingerprint fingerprint = new Fingerprint(uid, fp);
        try {
            this.insert(fingerprint);
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.select(fingerprint, fingerprint.objectUID());
    }

    public ArrayList<Table> getFingerprint(String uid) {
        Fingerprint fingerprint = new Fingerprint(uid);
        return this.select(fingerprint, fingerprint.objectUID());
    }

    public void deleteFingerprint(String uid) {
        try {
            this.delete(new Fingerprint(uid));
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Table> listFingerprint() {
        return this.select(new Fingerprint());
    }

//Table Record+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public ArrayList<String> selectRecord(User user) {
        ArrayList<String> result = new ArrayList();
        Record record = new Record();
        Register register = new Register();
        record.setUSER(user.getUID());
        ArrayList<Table> select = this.select(record, record.objectUSER());
        for (int i = 0; i < select.size(); i++) {
            record = (Record) select.get(i);
            register.setRID(record.getREGISTER());
            ArrayList<Table> reg = this.select(register, register.objectRID());
            if (reg.size() > 0) {
                register = (Register) reg.get(0);
            }
            result.add(record.getTIME() + "_" + register.getNAME());
        }
        return result;
    }

    public ArrayList<Table> selectStoreExpend(User store) {
        Cost cost = new Cost();
        cost.setSTORE(store.getUID());
        return this.select(cost, cost.objectSTORE());
    }

    public ArrayList<String> selectExpend(User user) {
        ArrayList<String> result = new ArrayList();
        Cost cost = new Cost();
        User store = new User();
        cost.setUSER(user.getUID());
        ArrayList<Table> select = this.select(cost, cost.objectUSER());
        for (int i = 0; i < select.size(); i++) {
            cost = (Cost) select.get(i);
            store.setUID(cost.getSTORE());
            ArrayList<Table> sto = this.select(store, store.objectUID());
            if (sto.size() > 0) {
                store = (User) sto.get(0);
            }
            result.add(cost.getTIME() + "_" + store.getLAST_NAME() + store.getFIRST_NAME() + "_" + cost.getPOINTS());
        }
        return result;
    }

    /**
     * 計算總點數
     *
     * @param user 使用者
     * @return 總點數
     */
    public int plusPoints(User user) {
        int point = 0;
        Record record = new Record();
        record.setUSER(user.getUID());
        ArrayList<Table> list_record = this.select(record, record.objectUSER());
        for (int i = 1; i < list_record.size(); i++) {
            int reg_a = ((Record) list_record.get(i - 1)).getREGISTER();
            int reg_b = ((Record) list_record.get(i)).getREGISTER();
            Miles mile = new Miles();
            mile.setREGISTER_A(reg_a);
            mile.setREGISTER_B(reg_b);
            ArrayList list = this.select(mile, mile.objectREGISTER_A(), mile.objectREGISTER_B());
            if (list.isEmpty()) {
                mile.setREGISTER_A(reg_b);
                mile.setREGISTER_B(reg_a);
                list = this.select(mile, mile.objectREGISTER_A(), mile.objectREGISTER_B());
            }
            if (list.size() > 0) {
                point += ((Miles) list.get(0)).getMETER();
            }
        }
        return point;
    }

    /**
     * 計算消費點數
     *
     * @param user 使用者
     * @return 消費點數
     */
    public int costPoints(User user) {
        int point = 0;
        Cost cost = new Cost();
        cost.setUSER(user.getUID());
        ArrayList<Table> list_cost = this.select(cost, cost.objectUSER());
        for (int i = 0; i < list_cost.size(); i++) {
            point += ((Cost) list_cost.get(i)).getPOINTS();
        }
        return point;
    }
}
