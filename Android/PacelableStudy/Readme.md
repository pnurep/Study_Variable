# Parcelable을 사용해 객체를 전달한다.

Java에서 제공하는 Serializable대신 안드로이드에서 더 빠른 성능을 보인다고 하는
Parcelable을 사용해 본다.

## 구현
1. 모델클래스에 Parcelable을 구현한다
2. CREATOR 변수를 만든다.
3. CREATOR 변수가 사용할 생성자 :  클래스명(Parcel in) 을 만든다
4. 외부에서 객체생성시 초기화를 위한 생성자를 만든다.
5. @Override method describeContents는 FILE_DESCRIPTOR일 경우 외에는 수정할 필요 없음.
6. 생성한 객체를 putExtra로 Intent 객체에 넣고 startActivity, (ArrayList일 경우에는 putParcelableArrayListExtra())
7. 받는쪽에서는 getParcelable() 또는 getParcelableArrayListExtra()로 가져오면 된다.