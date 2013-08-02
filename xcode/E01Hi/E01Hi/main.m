//
//  E01Hi.m
//  E01Hi
//
//  Created by plter on 13-4-3.
//  Copyright (c) 2013年 plter. All rights reserved.
//

#import "FlashRuntimeExtensions.h"


FREObject sayHi(
                FREContext ctx,
                void*      functionData,
                uint32_t   argc,
                FREObject  argv[]
                ){
    
    const uint8_t *value = (uint8_t*)"Hello AIR";
    FREObject obj;
    FRENewObjectFromUTF8(strlen((const char*)value), value, &obj);
    return obj;
}


void HiContextInitializer(void* extData, const uint8_t* ctxType, FREContext ctx, uint32_t* numFunctionsToSet, const FRENamedFunction** functionsToSet){
    
    *numFunctionsToSet = 1;
    FRENamedFunction* func = (FRENamedFunction*) malloc(sizeof(FRENamedFunction) * *numFunctionsToSet);
    
    func[0].name = (uint8_t*)"sayHi";
    func[0].functionData = NULL;
    func[0].function = &sayHi;
    
    *functionsToSet = func;
}

extern void freInit(
             void**                 extDataToSet       ,
             FREContextInitializer* ctxInitializerToSet,
             FREContextFinalizer*   ctxFinalizerToSet
             ){
    
    *ctxInitializerToSet=&HiContextInitializer;
}
