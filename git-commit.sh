#!/bin/bash
#https://www.cnblogs.com/reasonzzy/p/11653895.html

cd /home/ubuntu/libo/ngrok/hello-plugin

git status

git add -A

git commit -a -m `date +'%Y_%m_%d_%H_%M_%S'`提交

git push

exit 1
 
