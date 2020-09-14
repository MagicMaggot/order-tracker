# Order-tracker
 datasource.properties: Datasource configuration. 
 
 config.properties: 
 
 - configure path to the XML-file that uploads Products to the database. By default it reads /WEB-INF/productList.xml
 
 - configure path to the XML-file that will hold retrieved Products from the database. By default it saves to the desktop: productList.xml

===== REST Endpoints =====

=== Products:

@GET\
/order-tracker/api/products - get list of all products.

@GET\
/order-tracker/api/products/{serial} - get product by serial. If product not found, returns an error.

@POST\
/order-tracker/api/products - save product to the database. If serial number already exists, returns an error.

@PUT\
/order-tracker/api/products - updates existing product in the database. If item with this serial is not present - saves this item.

@DELETE\
/order-tracker/api/products/{serial} - deletes product if no related orderedItems are present. If item with this serial is not present, returns an error.

@GET\
/order-tracker/api/products/list/save - saves products from the database to an XML-file, configured in config.properties.

@POST\
/order-tracker/api/products/list/refresh - uplods products to the database from the XML-file, configured in config.properties.

=== Orders:

@GET\
/order-tracker/api/orders - get list of all orders.

@GET\
/order-tracker/api/orders/{orderId} - get order by id. If order is not found, returns an error.

@POST\
/order-tracker/api/orders - save order to the database. It always saves as a new order, even if ID is provided in the request body. Requires customerName, customerAddress and total.

@PUT\
/order-tracker/api/orders - update existing order (only provide id and fields that will be updated). If order with provided ID does not exist, behaves as previous method - saves payload as a new order.

@DELETE\
/order-tracker/api/orders/{orderId} - delete order and all related orderedItems. If order does not exist, returns an error.

@@GET\
/order-tracker/api/orders/{orderId}/items - get all items associated with this order. If order does not exist, returns an error.

@GET\
/order-tracker/api/orders/{orderId}/items/{itemId} - get particular item from particular order, or else returns an error.

@POST\
/order-tracker/api/orders/{orderId}/items - add an item to the order, if an order does not already have this item (else returns an error). If the order or product (serial) is not found, returns an error.

@PUT\
/order-tracker/api/orders/{orderId}/items - update an item in the order. Item payload only needs [quantity and (orderedItem.id OR product.serial)] fields. If an id is present. Provided only orderedItem.id, sets quantity from the payload. Provided only product.serial, checks if such item exists in the order and then sets quantity from the payload; if item does not exist in this order, then it adds this item to the order. Provided orderedItem.id AND product.serial - finds orderedItem by id and sets product by serial. If product with provided serial is not found - returns an error.

@DELETE\
/order-tracker/api/orders/{orderId}/items/{itemId} - delete orderedItem from the order. If item is not found,  returns an error.
