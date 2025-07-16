package com.pawansingh.to_dolist;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SwipeToActionCallback extends ItemTouchHelper.Callback {

    private SwipeActionListener listener;

    public interface SwipeActionListener {
        void onSwipeLeft(int position);
        void onSwipeRight(int position);
    }

    public SwipeToActionCallback(SwipeActionListener listener) {
        this.listener = listener;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(0, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();

        if (direction == ItemTouchHelper.LEFT) {
            listener.onSwipeLeft(position);
        } else if (direction == ItemTouchHelper.RIGHT) {
            listener.onSwipeRight(position);
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            View itemView = viewHolder.itemView;

            Paint paint = new Paint();
            paint.setTextSize(50);
            paint.setColor(Color.WHITE);
            paint.setTextAlign(Paint.Align.CENTER);

            if (dX > 0) {
                paint.setColor(Color.RED);
                c.drawRect(itemView.getLeft(), itemView.getTop(),
                        itemView.getLeft() + dX, itemView.getBottom(), paint);

                paint.setColor(Color.WHITE);
                c.drawText("🗑️", itemView.getLeft() + dX/2,
                        itemView.getTop() + itemView.getHeight()/2 + 15, paint);
            } else if (dX < 0) {
                paint.setColor(Color.GREEN);
                c.drawRect(itemView.getRight() + dX, itemView.getTop(),
                        itemView.getRight(), itemView.getBottom(), paint);

                paint.setColor(Color.WHITE);
                c.drawText("✓", itemView.getRight() + dX/2,
                        itemView.getTop() + itemView.getHeight()/2 + 15, paint);
            }
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}