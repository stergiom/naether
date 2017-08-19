source "http://rubygems.org"

gem "httpclient", "2.8.3", git: "https://github.com/stergiom/httpclient", branch: "fix-for-too-big-for-int"

platform = $platform || RUBY_PLATFORM[/java/] || 'ruby'
 if platform != 'java'
  gem 'rjb', '> 1.4.0', '< 1.6.0'
end

group :development do
  gem "rspec", "> 2.9.0"
  gem "jeweler", "~> 2.0.0"
  gem "yard"
  gem "kramdown"
end
