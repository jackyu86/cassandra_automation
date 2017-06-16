# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# These properties are used with GossipingPropertyFileSnitch and will
# indicate the rack and dc for this node
dc=${cfg.dc}
rack=RAC1

# Add a suffix to a datacenter name. Used by the Ec2Snitch and Ec2MultiRegionSnitch
# to append a string to the EC2 region name.
#dc_suffix=

# Uncomment the following line to make this snitch prefer the internal ip when possible, as the Ec2MultiRegionSnitch does.
# prefer_local=true
