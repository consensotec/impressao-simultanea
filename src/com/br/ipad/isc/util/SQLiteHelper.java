
package com.br.ipad.isc.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {

	private String[] scriptSQLCreate;
	private String[] scriptSQLDelete;

	public SQLiteHelper(Context context, String nomeBanco, int versaoBanco, String[] scriptSQLCreate, String[] scriptSQLDelete) {
		super(context, nomeBanco, null, versaoBanco);
		this.scriptSQLCreate = scriptSQLCreate;
		this.scriptSQLDelete = scriptSQLDelete;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		int qtdeScripts = scriptSQLCreate.length;
		Log.w(ConstantesSistema.CATEGORIA, "Current db version is " + db.getVersion());
		for (int i = 0; i < qtdeScripts; i++) {
			String sql = scriptSQLCreate[i];
			db.execSQL(sql);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int versaoAntiga, int novaVersao) {
		int qtdeScripts = scriptSQLDelete.length;
		Log.w(ConstantesSistema.CATEGORIA, "Old Version " + versaoAntiga + " New Version " + novaVersao + " db.getVersion is " + db.getVersion());
		for (int i = 0; i < qtdeScripts; i++) {
			String sql = scriptSQLDelete[i];
			db.execSQL(sql);
		}
		onCreate(db);
	}
}