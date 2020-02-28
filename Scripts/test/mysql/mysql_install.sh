 # Install mysql server
sudo apt-get update
sudo apt-get install mysql-server

# Run configuration tool
sudo mysql_secure_installation utility

# Set mysql to launch on reboot
sudo systemctl enable mysql
