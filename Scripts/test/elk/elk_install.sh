# Install Java
sudo apt-get update
sudo apt-get install default-jre

# Download elastic search repository key
wget -qO - https://artifacts.elastic.co/GPG-KEY-elasticsearch | sudo apt-key add -

# Add elasti search respository
echo "deb https://artifacts.elastic.co/packages/7.x/apt stable main" | sudo tee -a /etc/apt/sources.list.d/elastic-7.x.list

# Update
sudo apt-get update

# Install Elastic Search
sudo apt-get install elasticsearch

# Start Elastic Search
sudo systemctl enable elasticsearch
sudo systemctl start elasticsearch

# Install Kibana
sudo apt-get install kibana

# Start Kibana
sudo systemctl enable kibana
sudo systemctl start kibana

# Install Metric Beat
sudo apt-get install metricbeat

# Start Metric Beat
sudo service metricbeat start
