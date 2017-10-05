[Ivy]
15EEB6E4567F1390 3.20 #module
>Proto >Proto Collection #zClass
pp0 personWsSoap Big #zClass
pp0 WS #cInfo
pp0 #process
pp0 @TextInP .webServiceName .webServiceName #zField
pp0 @TextInP .implementationClassName .implementationClassName #zField
pp0 @TextInP .authenticationType .authenticationType #zField
pp0 @TextInP .resExport .resExport #zField
pp0 @TextInP .type .type #zField
pp0 @TextInP .processKind .processKind #zField
pp0 @AnnotationInP-0n ai ai #zField
pp0 @MessageFlowInP-0n messageIn messageIn #zField
pp0 @MessageFlowOutP-0n messageOut messageOut #zField
pp0 @TextInP .xml .xml #zField
pp0 @TextInP .responsibility .responsibility #zField
pp0 @StartWS ws0 '' #zField
pp0 @EndWS ws1 '' #zField
pp0 @GridStep f1 '' #zField
pp0 @PushWFArc f2 '' #zField
pp0 @PushWFArc f0 '' #zField
>Proto pp0 pp0 personWsSoap #zField
pp0 ws0 inParamDecl '<com.axonivy.connectivity.Person person> param;' #txt
pp0 ws0 inParamTable 'out.person=param.person;
' #txt
pp0 ws0 outParamDecl '<com.axonivy.connectivity.Person registrar> result;
' #txt
pp0 ws0 outParamTable 'result.registrar=in.person;
' #txt
pp0 ws0 actionDecl 'com.axonivy.connectivity.ws.PersonServiceData out;
' #txt
pp0 ws0 callSignature hello(com.axonivy.connectivity.Person) #txt
pp0 ws0 useUserDefinedException false #txt
pp0 ws0 taskData TaskTriggered.PRI=2 #txt
pp0 ws0 type com.axonivy.connectivity.ws.PersonServiceData #txt
pp0 ws0 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>hello(Person)</name>
        <nameStyle>13,5,7
</nameStyle>
    </language>
</elementInfo>
' #txt
pp0 ws0 @C|.responsibility Everybody #txt
pp0 ws0 81 49 30 30 -42 17 #rect
pp0 ws0 @|StartWSIcon #fIcon
pp0 ws1 type com.axonivy.connectivity.ws.PersonServiceData #txt
pp0 ws1 337 49 30 30 0 15 #rect
pp0 ws1 @|EndWSIcon #fIcon
pp0 f1 actionDecl 'com.axonivy.connectivity.ws.PersonServiceData out;
' #txt
pp0 f1 actionTable 'out=in;
' #txt
pp0 f1 actionCode 'ivy.log.info("ws data : "+in.person);' #txt
pp0 f1 type com.axonivy.connectivity.ws.PersonServiceData #txt
pp0 f1 168 42 112 44 0 -7 #rect
pp0 f1 @|StepIcon #fIcon
pp0 f2 expr out #txt
pp0 f2 111 64 168 64 #arcP
pp0 f0 expr out #txt
pp0 f0 280 64 337 64 #arcP
>Proto pp0 .webServiceName com.axonivy.connectivity.personWsSoap #txt
>Proto pp0 .authenticationType 'WebService Security' #txt
>Proto pp0 .type com.axonivy.connectivity.ws.PersonServiceData #txt
>Proto pp0 .processKind WEB_SERVICE #txt
>Proto pp0 -8 -8 16 16 16 26 #rect
>Proto pp0 '' #fIcon
pp0 ws0 mainOut f2 tail #connect
pp0 f2 head f1 mainIn #connect
pp0 f1 mainOut f0 tail #connect
pp0 f0 head ws1 mainIn #connect
