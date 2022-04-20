```
Invocation of init method failed; nested exception is org.hibernate.DuplicateMappingException: 
Table [tbl_inventory] contains physical column name [item_size_id] referred to by multiple logical column names:
[item_size_id], [itemSizeId]
```
<br>
위와 같은 에러를 만났을 때는 Entity를 확인해봐야 한다.

```java
@Entity
@IdClass(InventoryId.class)
@Table(name = "tbl_inventory")
public class Inventory {

    @Id
    private Long sellId;

    @Id
    private Long itemSizeId;

    @Column(nullable = false)
    private Long amount;

    @MapsId
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sell_id")
    private Sell sell;

    @MapsId
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_size_id")
    private ItemSize itemSize;
    
    ...
```

@Id 설정이 되어 있는 칼럼의 name을 따로 지정해주면 오류가 짠 해결된다.

```java
    @Id
    @Column(name = "sell_id")
    private Long sellId;

    @Id
    @Column(name = "item_size_id")
    private Long itemSizeId;
```
