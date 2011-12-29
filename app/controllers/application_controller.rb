class ApplicationController < ActionController::Base
  protect_from_forgery # CXRF protection

  # dont render any action that cancan hasn't authorized.
  check_authorization :unless => :allowed_vendor_controller?

  def allowed_vendor_controller?
    false ||
      devise_controller? || # devise does authentication itself
      self.class == SwitchUserController # See SwitchUser#setup
  end


  # Specify the layout for the controller; rails looks for a layout with the
  # name of the controller first, and only comes here when it can't find it. For
  # devise, we use the "outer application" layout, currently named "join" :-/
  layout :layout_by_resource
  protected
  def layout_by_resource
    if devise_controller?
      "join"
    else
      "application"
    end
  end

end
