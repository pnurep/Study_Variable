#CollapsingLayout을 사용해 본다
* Collapsing Layout
* RecyclerView
* CardView

## 설명
* 스크롤이 생기는 요소에는 android:layout_behavior or app:layout_behavior 속성을 appbar_scrolling_view_behavior로 설정해야 한다.
* 리스트가 스크롤 될 때 view가 연동되게 하려면 (사라지거나 나타나게) app:layout_scrollFlags 속성을 설정해야한다
    * scroll : 이 뷰가 화면에서 사라질 수 있음을 나타낸다 이 값이 설정되지 않으면 이 뷰는 화면 위쪽에 항상 남아 있는다.
    * enterAlways : scroll옵션과 같이 사용될 때 위쪽으로 스크롤하는 경우는 사라지고 아래쪽으로 스크롤 하는 경우는 다시 나타난다.
    * enterAlwaysCollapsed : enterAlways와 유사하지만 아래쪽으로 스크롤하는 경우만 다르다. 스크롤되는 리스트의 끝에 도달했을때만 이 뷰가 다시 나타난다. 만일 minHeight 속성을 설정하면 그 값에 도달했을때만 이 뷰가 다시 나타난다. 이 옵션은 scroll과 enterAlways 모두를 같이 사용했을때만 동작한다.
'''
app:layout_scrollsFlags="scroll|enterAlways|enterAlwaysCollapsed"
android:minHeight="20dp"
'''
    * exitUntilCollapsed : 이 값이 설정되면 위쪽으로 스크롤하는 동안 minHeight에 도달할 때 까지만 이 뷰가 사라진다. 그리고 스크롤 방향이 변경될 때 까지는 minHeight지점에 남아 있는다. </br>
    
    
* CollapsingToolbarLayout 컨테이너는 표준 툴바를 개선한 것으로, 스크롤 액션과 연동하여 앱바와 그것의 자식을이 보이거나 사라지게 하는 광범위한 옵션과 제어수준을 제공한다.
레이아웃파일에서 CollapsingToolbarLayout은 AppBarLayout의 자식으로 추가되며, 툴바가 화면에서 사라지거나 나타날때 툴바 제목의 폰트크기를 자동으로 조정하는 것과 같은 기능을 제공한다.
이때 CollapsingMode를 parallax로 지정하면 앱바가 사라질때 그것의 Content가 뷰에서 사라진다. 반면에 pin으로 설정해두면 앱바의 요소들이 항상 고정위치에 남아있게 된다. 또한 scrim으로 지정하면 툴바가 사라지는 동안 전환되는 색상을 지정할 수 있다.


 
