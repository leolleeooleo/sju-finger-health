

#ifndef __CLIENT_H__
#define __CLIENT_H__

extern void CLIENT_connect();
extern void CLIENT_login();
extern unsigned char PROTOCOL_action(unsigned char);
extern unsigned char * CLIENT_getName();
extern unsigned char * CLIENT_getPoint();



#endif