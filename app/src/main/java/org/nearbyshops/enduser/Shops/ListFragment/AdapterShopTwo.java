package org.nearbyshops.enduser.Shops.ListFragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.nearbyshops.enduser.Model.Shop;
import org.nearbyshops.enduser.MyApplication;
import org.nearbyshops.enduser.R;
import org.nearbyshops.enduser.ShopDetail.ShopDetail;
import org.nearbyshops.enduser.ShopHome.ShopHome;
import org.nearbyshops.enduser.Utility.UtilityGeneral;
import org.nearbyshops.enduser.Utility.UtilityShopHome;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sumeet on 25/5/16.
 */
public class AdapterShopTwo extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<Shop> dataset = null;
    private Context context;
    private Fragment fragment;


    public AdapterShopTwo(List<Shop> dataset, Context context, Fragment fragment) {
        this.dataset = dataset;
        this.context = context;
        this.fragment = fragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;

        if(viewType==0)
        {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_shop,parent,false);

            return new ViewHolder(view);
        }
        else
        {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_progress_bar,parent,false);

            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder_given, int position) {


        if(holder_given instanceof AdapterShopTwo.ViewHolder)
        {
            AdapterShopTwo.ViewHolder holder = (AdapterShopTwo.ViewHolder)holder_given;

            Shop shop = dataset.get(position);

/*
            holder.shopName.setText(dataset.get(position).getShopName());


            if(shop.getRt_rating_count()==0)
            {
                holder.rating.setText("N/A");
            }
            else
            {
                holder.rating.setText(String.format( "%.2f", dataset.get(position).getRt_rating_avg()));
            }



            holder.distance.setText(String.format( "%.2f", dataset.get(position).getDistance() )+ " Km");

//        Log.d("applog","on BInd()");

            String imagePath = UtilityGeneral.getImageEndpointURL(MyApplication.getAppContext())
                    + dataset.get(position).getImagePath();

            Picasso.with(context)
                    .load(imagePath)
                    .placeholder(R.drawable.nature_people)
                    .into(holder.shopImage);
*/


            if(shop!=null)
            {
                holder.shopName.setText(shop.getShopName());

                if(shop.getShopAddress()!=null)
                {
                    holder.shopAddress.setText(shop.getShopAddress() + "\n" + String.valueOf(shop.getPincode()));
                }

                String imagePath = UtilityGeneral.getImageEndpointURL(MyApplication.getAppContext())
                        + shop.getLogoImagePath();

                Drawable placeholder = VectorDrawableCompat
                        .create(context.getResources(),
                                R.drawable.ic_nature_people_white_48px, context.getTheme());

                Picasso.with(context)
                        .load(imagePath)
                        .placeholder(placeholder)
                        .into(holder.shopLogo);

                holder.delivery.setText("Delivery : Rs " + String.format( "%.2f", shop.getDeliveryCharges()) + " per order");
                holder.distance.setText("Distance : " + String.format( "%.2f", shop.getRt_distance()) + " Km");


                if(shop.getRt_rating_count()==0)
                {
                    holder.rating.setText("N/A");
                    holder.rating_count.setText("( Not Yet Rated )");

                }
                else
                {
                    holder.rating.setText(String.valueOf(shop.getRt_rating_avg()));
                    holder.rating_count.setText("( " + String.format( "%.0f", shop.getRt_rating_count()) + " Ratings )");
                }


                if(shop.getShortDescription()!=null)
                {
                    holder.description.setText(shop.getShortDescription());
                }

            }


        }
        else if(holder_given instanceof LoadingViewHolder)
        {
            AdapterShopTwo.LoadingViewHolder viewHolder = (LoadingViewHolder) holder_given;

            int itemCount = 0;

            if(fragment instanceof  FragmentShopTwo)
            {
                itemCount = ((FragmentShopTwo) fragment).getItemCount();
            }


            if(position == 0 || position == itemCount)
            {
                viewHolder.progressBar.setVisibility(View.GONE);
            }
            else
            {
                viewHolder.progressBar.setVisibility(View.VISIBLE);
                viewHolder.progressBar.setIndeterminate(true);

            }
        }



    }

    @Override
    public int getItemCount() {

//        Log.d("applog",String.valueOf(dataset.size()));

        return (dataset.size()+1);
    }


    @Override
    public int getItemViewType(int position) {
        super.getItemViewType(position);

        if(position==dataset.size())
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }

    public class LoadingViewHolder extends  RecyclerView.ViewHolder{

        @Bind(R.id.progress_bar)
        ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

//        TextView shopName;
//        ImageView shopImage;
//        TextView distance;
//        TextView rating;
//        RelativeLayout listItem;



        @Bind(R.id.shop_name)
        TextView shopName;

        @Bind(R.id.shop_address)
        TextView shopAddress;

        @Bind(R.id.shop_logo)
        ImageView shopLogo;

        @Bind(R.id.delivery)
        TextView delivery;

        @Bind(R.id.distance)
        TextView distance;

        @Bind(R.id.rating)
        TextView rating;

        @Bind(R.id.rating_count)
        TextView rating_count;

        @Bind(R.id.description)
        TextView description;

        @Bind(R.id.shop_info_card)
        CardView list_item;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            ButterKnife.bind(this,itemView);


//            listItem = (RelativeLayout) itemView.findViewById(R.id.list_item_shop);
//            rating = (TextView) itemView.findViewById(R.id.rating);
//            distance = (TextView) itemView.findViewById(R.id.distance);
//            shopName = (TextView) itemView.findViewById(R.id.shopName);
//            shopImage = (ImageView) itemView.findViewById(R.id.shopImage);

//            shopImage.setOnClickListener(this);
//            listItem.setOnClickListener(this);
        }



        @OnClick(R.id.shop_info_card)
        void listItemClick()
        {

            Intent shopHomeIntent = new Intent(context, ShopHome.class);
            UtilityShopHome.saveShop(dataset.get(getLayoutPosition()),context);
            context.startActivity(shopHomeIntent);
        }


        @OnClick(R.id.shop_logo)
        void shopLogoClick()
        {
            Intent intent = new Intent(context, ShopDetail.class);
            intent.putExtra(ShopDetail.SHOP_DETAIL_INTENT_KEY,dataset.get(getLayoutPosition()));
            context.startActivity(intent);
        }


        @Override
        public void onClick(View v) {

            //Toast.makeText(context,"Item Click : " + String.valueOf(getLayoutPosition()),Toast.LENGTH_SHORT).show();

            switch (v.getId())
            {
                case R.id.shopImage:


                    break;

                case R.id.list_item_shop:

                    Intent shopHomeIntent = new Intent(context, ShopHome.class);
                    UtilityShopHome.saveShop(dataset.get(getLayoutPosition()),context);
                    context.startActivity(shopHomeIntent);

                    break;

                default:
                    break;
            }
        }
    }
}
