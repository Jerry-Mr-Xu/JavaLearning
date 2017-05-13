package com.jerry.javalearning.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jerry.javalearning.BR;
import com.jerry.javalearning.R;
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
	private OnItemClickListener onItemClickListener;
	private OnItemDeleteClickListener onItemDeleteClickListener;

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
		switch (view.getId())
		{
			case R.id.tv_point:
				if (onItemClickListener != null)
				{
					onItemClickListener.onItemClick(ListShowAdapter.this, (Integer) view.getTag());
				}
				break;

			case R.id.iv_item_delete:
				if (onItemDeleteClickListener != null)
				{
					onItemDeleteClickListener.onItemDeleteClick(ListShowAdapter.this, (Integer) ((View) view.getParent()).getTag());
				}
				break;
		}
	}

	public void setOnItemClickListener(OnItemClickListener onItemClickListener)
	{
		this.onItemClickListener = onItemClickListener;
	}

	public void setOnItemDeleteClickListener(OnItemDeleteClickListener onItemDeleteClickListener)
	{
		this.onItemDeleteClickListener = onItemDeleteClickListener;
	}

	public interface OnItemClickListener
	{
		void onItemClick(ListShowAdapter adapter, int position);
	}

	public interface OnItemDeleteClickListener
	{
		void onItemDeleteClick(ListShowAdapter adapter, int position);
	}

	class ItemViewHolder extends RecyclerView.ViewHolder
	{
		private final ViewDataBinding binding;

		ItemViewHolder(ViewDataBinding binding)
		{
			super(binding.getRoot());
			this.binding = binding;
			this.binding.setVariable(BR.listener, ListShowAdapter.this);
			this.binding.setVariable(BR.clickListener, ListShowAdapter.this);
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
