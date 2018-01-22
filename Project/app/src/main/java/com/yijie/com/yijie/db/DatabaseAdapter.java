package com.yijie.com.yijie.db;

import java.util.ArrayList;
import java.util.List;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yijie.com.yijie.db.DatabaseHelper;
import com.yijie.com.yijie.db.DatabaseManager;

import org.w3c.dom.Node;

/**
 * 数据库操作类
 *
 * @author zihao
 * @param <B>
 *
 */
public class DatabaseAdapter<B> {

	private static DatabaseManager manager;
	private static Context mContext;

	/**
	 * 获取一个操作类对象
	 *
	 * @param context
	 * @return
	 */
	public static DatabaseAdapter getIntance(Context context) {
		DatabaseAdapter adapter = new DatabaseAdapter();
		mContext = context;
		manager = DatabaseManager.getInstance(new DatabaseHelper(mContext));
		return adapter;
	}

	/**
	 * 插入信息
	 *
	 * @param
	 */
	public void inserInfo(ContactBean contactBean) {
		SQLiteDatabase database = manager.getWritableDatabase();

		try {

				ContentValues values = new ContentValues();
				values.put("name", contactBean.getName());
				values.put("phoneNumber",contactBean.getPhoneNumber());
			values.put("zjNumber",contactBean.getZjNubmer());
			values.put("wxNumber",contactBean.getWxNubmer());
			values.put("qqNumber",contactBean.getQqNubmer());

			values.put("schoolSample", contactBean.getSchoolSample());
			values.put("schoolEduction",contactBean.getSchoolEduction());
			values.put("schoolMonth",contactBean.getSchoolMonth());
			values.put("schoolType",contactBean.getSchoolType());
			values.put("schoolLine",contactBean.getSchoolLine());
			values.put("schoolMode",contactBean.getSchoolMode());
			values.put("schoolTime",contactBean.getSchoolTime());

           database.insert(DatabaseHelper.TABLE_NAME, null, values);


        } catch (Exception e) {
			// TODO: handle exception
		} finally {
			manager.closeDatabase();

		}


	}

	/**
	 * 查询所有联系人
	 * @param <
	 *
	 * @param
	 *
	 * @return
	 */
	public List<ContactBean> queryAll() {
		List<ContactBean> resultArray = new ArrayList<ContactBean>();
		SQLiteDatabase database = manager.getReadableDatabase();
		Cursor cursor = null;

		try {


			cursor = database.rawQuery("select * from "
					+ DatabaseHelper.TABLE_NAME
					, null);

			while (cursor.moveToNext()) {

				ContactBean contactBean = new ContactBean();
				contactBean.setId(cursor.getString(cursor.getColumnIndex("id")));
				contactBean.setName(cursor.getString(cursor.getColumnIndex("name")));
				contactBean.setPhoneNumber(cursor.getString(cursor.getColumnIndex("phoneNumber")));
                contactBean.setZjNubmer(cursor.getString(cursor.getColumnIndex("zjNumber")));
                contactBean.setWxNubmer(cursor.getString(cursor.getColumnIndex("wxNumber")));
                contactBean.setQqNubmer(cursor.getString(cursor.getColumnIndex("qqNumber")));

				contactBean.setSchoolSample(cursor.getString(cursor.getColumnIndex("schoolSample")));
				contactBean.setSchoolEduction(cursor.getString(cursor.getColumnIndex("schoolEduction")));
				contactBean.setSchoolMonth(cursor.getString(cursor.getColumnIndex("schoolMonth")));
				contactBean.setSchoolType(cursor.getString(cursor.getColumnIndex("schoolType")));
				contactBean.setSchoolLine(cursor.getString(cursor.getColumnIndex("schoolLine")));
				contactBean.setSchoolMode(cursor.getString(cursor.getColumnIndex("schoolMode")));
				contactBean.setSchoolTime(cursor.getString(cursor.getColumnIndex("schoolTime")));



				resultArray.add(contactBean);

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.toString();
		} finally {
			manager.closeDatabase();
		}

		return resultArray;
	}


	/**
	 * 删除表中的所有数据
	 */
	public void deleteAll() {
		SQLiteDatabase database = manager.getWritableDatabase();

		try {
			database.delete(DatabaseHelper.TABLE_NAME, null, null);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			manager.closeDatabase();
		}
	}


	/**
	 * 根据id删除表中数据
	 */
	public void deleteContactById(String id) {
		SQLiteDatabase database = manager.getWritableDatabase();

		try {
			database.delete(DatabaseHelper.TABLE_NAME, "id=?",  new String[]{id});

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			manager.closeDatabase();
		}
	}




    /**
     * 更新一条联系人
     * @param bean
     */
    public void update(ContactBean bean){

        SQLiteDatabase database = manager.getWritableDatabase();

        try {

            ContentValues values = new ContentValues();
            values.put("name",bean.getName());
            values.put("phoneNumber",bean.getPhoneNumber());
            values.put("zjNumber",bean.getZjNubmer());
            values.put("wxNumber",bean.getWxNubmer());
            values.put("qqNumber",bean.getQqNubmer());

			values.put("schoolSample", bean.getSchoolSample());
			values.put("schoolEduction",bean.getSchoolEduction());
			values.put("schoolMonth",bean.getSchoolMonth());
			values.put("schoolType",bean.getSchoolType());
			values.put("schoolLine",bean.getSchoolLine());
			values.put("schoolMode",bean.getSchoolMode());
			values.put("schoolTime",bean.getSchoolTime());



			database.update(DatabaseHelper.TABLE_NAME, values, "id=?", new String[]{bean.getId()});


        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            manager.closeDatabase();
        }




    }

}