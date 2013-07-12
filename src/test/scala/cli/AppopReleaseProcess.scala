package cli

object AppopReleaseProcess extends App {

  val svn_trunk = "TODO"
  val target_dir = "release"

  var cmd = ""

  cmd = s"""
cd ~/tmp
rm -rf $target_dir
svn checkout $svn_trunk $target_dir
cd release

rm /tmp/ChangeLog
mv ChangeLog /tmp/ChangeLog  
(changelog.sh . 1 ; cat /tmp/ChangeLog | cat ) > ChangeLog
svn commit ChangeLog -m "updated ChangeLog"
head ChangeLog

mvn clean install -P production"""

  cmd =
    s"""mvn release:prepare release:perform -P production 

#svn ls https://svn.1and1.org/svn/SwisApps/j2ee/apps/termination/termination-clearance/tags/ | sort -V | tail -1

appop-release.sh -T https://svn.1and1.org/svn/SwisApps/j2ee/apps/cancellationServiceCore/tags/cancellationServiceCore-3.19.4 -Pqa,production 

http://issue.tool.1and1.com/secure/CreateIssueDetails!init.jspa?pid=12341&issuetype=162&components=14452&priority=3&summary=Deployment+of+cancellationServiceCore:3.19.4&customfield_11198=https://inside.1and1.org/development/yp/view?id=5804&description=%2Aac1%2A%0A%0Ahttps://svn.1and1.org/svn/swis%5Fartefakt/ac1/cancellationServiceCore/4/3.19.4/cancellationServiceCore-v3.19.ear%0A%7C%7CCLUSTER%7C%7CSLOT%7C%7CPREFIX+TYPE%7C%7CPREFIX%7C%7C%0A%7Cac1swisbossa%7C1%7CFILE%7C4%7C%0A%2Aprod%2A%0A%0Ahttps://svn.1and1.org/svn/swis%5Fartefakt/prod/cancellationServiceCore/4/3.19.4/cancellationServiceCore-v3.19.ear%0A%7C%7CCLUSTER%7C%7CSLOT%7C%7CPREFIX+TYPE%7C%7CPREFIX%7C%7C%0A%7Cswisbossc%7C1%7CFILE%7C4%7C%0A%2AChangelog%2A%0Ahttps://svn.1and1.org/svn/SwisApps/j2ee/apps/cancellationServiceCore/tags/cancellationServiceCore-3.19.4/ChangeLog%0A&customfield_11669=cancellationServiceCore&customfield_11670=3.19.4&customfield_10968=49863914;49863920;49863926;49863932;49985462;49985473;49985484;49985495;&labels=deployment&labels=ScriptGenerated
"""

  def isNotEmpty = { str: String ⇒ str != null && !str.trim().isEmpty() }

  def commands = (str: String) ⇒ str.split('\n').filter(isNotEmpty)

}