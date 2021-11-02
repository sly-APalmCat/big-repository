**0、查看所有配置信息**

git config --list

**1、用户名、邮箱**

git config user.name [user.email]

**2、修改 用户名、邮箱**

git config --global user.name [user.email]  "your name[email]"

**3、当前用户名、邮箱修改**

cd  "your project"

git config user.name[user.email] "your name[email]"

**4、生成sshkey**

ssh-keygen -t rsa -C "your email"

ssh-add "~/.ssh/id_rsa"

**5、查看sshkey**

mac   cat ".ssh/id_rsa.pub"