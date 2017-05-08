package com.jerry.javalearning.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jerry.javalearning.BR;
import com.jerry.javalearning.module.BaseItemModule;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 列表展示适配器
 * <p>
 * Created by Jerry on 2017/5/8.
 */

public class ListShowAdapter extends RecyclerView.Adapter<ListShowAdapter.ItemViewHolder> implements View.OnClickListener
{
	private List<BaseItemModule> baseItemList;
	private OnItemClickListener listener;

	public ListShowAdapter(List<BaseItemModule> baseItemList)
	{
		this.baseItemList = baseItemList;
	}

	@Override
	public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		return new ItemViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false));
	}

	@Override
	public void onBindViewHolder(ItemViewHolder holder, int position)
	{
		holder.bindTo(baseItemList.get(position), position);
	}

	@Override
	public int getItemViewType(int position)
	{
		return baseItemList.get(position).getLayout();
	}

	@Override
	public int getItemCount()
	{
		return baseItemList.size();
	}

	@Override
	public void onClick(View view)
	{
		if (listener != null)
		{
			listener.onItemClick(ListShowAdapter.this, (Integer) view.getTag());
		}
	}

	public void setOnItemClickListener(OnItemClickListener listener)
	{
		this.listener = listener;
	}

	public interface OnItemClickListener
	{
		public void onItemClick(ListShowAdapter adapter, int position);
	}

	class ItemViewHolder extends RecyclerView.ViewHolder
	{
		private final ViewDataBinding binding;

		ItemViewHolder(ViewDataBinding binding)
		{
			super(binding.getRoot());
			this.binding = binding;
			this.binding.setVariable(BR.listener, ListShowAdapter.this);
		}

		void bindTo(BaseItemModule item, int position)
		{
			itemView.setTag(position);
			binding.setVariable(BR.item, item);
			binding.setVariable(BR.decimalFormat, new DecimalFormat("00.00"));
			binding.executePendingBindings();
		}
	}
}
