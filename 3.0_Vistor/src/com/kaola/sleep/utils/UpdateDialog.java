package com.kaola.sleep.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kaola.sleep.R;


/**
 * 自定义dialog
 * 
 */
public class UpdateDialog extends AlertDialog
{
	private static UpdateDialog updateDialog = null;
	private static String thistiptext = null;
	private static String cancletext = null;
	private static String confirmtext = null;
	static Context lastContext = null;

	public static UpdateDialog getInstance(Context context, int theme, String tiptext, String confirm, String cancle, OnCustomDialogListener customDialogListener)
	{
		_customDialogListener = customDialogListener;

		updateDialog = new UpdateDialog(context, theme);

		updateDialog.setCancelable(false);
		updateDialog.setCanceledOnTouchOutside(false);

		thistiptext = tiptext;
		cancletext = cancle;
		confirmtext = confirm;
		if (updateDialog.tv_etName != null)
			updateDialog.tv_etName.setText(thistiptext);
		if (updateDialog.tv_tocancel != null)
			updateDialog.tv_tocancel.setText(cancletext);
		if (updateDialog.tv_toconfirm != null)
			updateDialog.tv_toconfirm.setText(confirmtext);
		return updateDialog;
	}

	/**
	 * 定义回调事件，用于dialog的点击事件
	 *
	 */
	public interface OnCustomDialogListener
	{
		public void refuseUpdate(String name);

		public void doUpdate(String name);
	}

	private static OnCustomDialogListener _customDialogListener;

	TextView tv_tip,tv_contents;
	TextView tv_etName;
	TextView tv_toconfirm;
	TextView tv_tocancel;
	View view1;

	Context context;

	public UpdateDialog(Context context, int theme)
	{
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_dialog);
		// 设置标题
		tv_contents = (TextView) findViewById(R.id.tv_contents);

		tv_contents.setText(thistiptext);

		tv_toconfirm = (TextView) findViewById(R.id.tv_toconfirm);
		tv_tocancel = (TextView) findViewById(R.id.tv_tocancel);

		tv_toconfirm.setText(confirmtext);
		tv_tocancel.setText(cancletext);

		if (_customDialogListener == null || cancletext == null)
		{
			view1.setVisibility(View.GONE);
			tv_tocancel.setVisibility(View.GONE);
		}
		else
			tv_tocancel.setVisibility(View.VISIBLE);

		tv_toconfirm.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (_customDialogListener != null)
					_customDialogListener.doUpdate("");
				dismiss();
			}
		});

		tv_tocancel.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (_customDialogListener != null)
					_customDialogListener.refuseUpdate("");
				dismiss();
			}
		});
	}

	/**
	 * dialog的消失
	 */
	public static void dismissGeneralDialog()
	{
		if (updateDialog != null && updateDialog.isShowing())
		{
			updateDialog.dismiss();
		}
	}

	public static void destoryInstance()
	{
		if (updateDialog == null)
			return;
		
		updateDialog = null;
	}
}
