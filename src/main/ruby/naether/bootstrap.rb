require "#{File.dirname(__FILE__)}/java"

class Naether
  class Bootstrap
    class << self
      
      def dependencies( jar_path = nil )
        Naether::Java.load_jar_dirs( jar_path || Naether::JAR_LIB )
        
        if Naether.platform == 'java'
          return com.slackworks.Bootstrap.dependencies.to_a
        else
          bootstrap = Rjb::import('com.slackworks.Bootstrap')
          return bootstrap.dependencies.toArray().map{ |dep| dep.toString() }
        end   
        
      end
    end
  end
end