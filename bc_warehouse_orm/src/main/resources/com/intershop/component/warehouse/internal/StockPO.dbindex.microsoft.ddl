/*
 =============================================================================
 File: StockPO.dbindex.microsoft.ddl
 Generated by JGen Code Generator from INTERSHOP Communications AG.
 =============================================================================
 The JGen Code Generator software is the property of INTERSHOP Communications AG. 
 Any rights to use are granted under the license agreement. 
 =============================================================================
 */
 
VARIABLE recreate_indexes NUMBER;

EXEC :recreate_indexes := '&recreate_indexes';

PROMPT /* Class com.intershop.training.component.warehouse.internal.StockPO */
PROMPT -- Foreign key indices
EXEC staging_ddl.create_index('STOCK_FK999', 'STOCK', '(WAREHOUSEID)', 'dummytablespace', 'NONUNIQUE', :recreate_indexes);

PROMPT -- Explicit indices + inversion Entry key indices
EXEC staging_ddl.create_index('STOCK_IE001', 'STOCK', '(PRODUCTID,DOMAINID)', 'dummytablespace', 'NONUNIQUE', :recreate_indexes);
EXEC staging_ddl.create_index('STOCK_IE002', 'STOCK', '(WAREHOUSEID)', 'dummytablespace', 'NONUNIQUE', :recreate_indexes);


 