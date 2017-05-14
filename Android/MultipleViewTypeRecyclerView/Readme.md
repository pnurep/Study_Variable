# Multiple Views in RecyclerView

* 리사이클러 뷰 안에 여러 형태의 뷰를 구현.
* @Override getItemViewType 으로 뷰 아이템의 타입을 구별한다.
* 뷰의 각 타입마다 xml을 만들어준다.
* 어댑터에 들어갈 data 리스트는 Object타입이어야 한다 -> 리스트 내부에 담고 있어야할 데이터가 
하나의 타입으로 고정된것이 아니기 때문에!
* onBindViewholder 에서 holder.imageView에 이미지를 넣어줄때 귀찮아서 처음엔 setImageResource로 했는데
바로 OOM 일어남... -> Glide로 해결!  고로 갓 글라이드 씁시다.

