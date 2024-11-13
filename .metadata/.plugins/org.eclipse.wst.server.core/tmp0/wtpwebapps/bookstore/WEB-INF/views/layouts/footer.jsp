<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	@php $setting = App\Models\Setting::first(); $footer =
	App\Models\ThemeFooter::first(); $html = $social = $subscribe = ''; if
	(!empty($footer)) { $footer_menu_items =
	App\Models\MenuItem::where('menu_id',
	App\Models\MenuItem::FOOTER)->where('parent_id', 0)->get(); if
	($footer->social) { if (!empty($setting->facebook_url)) { $social .= '
	<a href="'.$setting->facebook_url.'" target="_blank"
		class="btn btn-facebook btn-light btn-icon"><i
		class="fab fa-facebook-f fa-fw"></i></a>'; } if
	(!empty($setting->youtube_url)) { $social .= '
	<a href="'.$setting->youtube_url.'" target="_blank"
		class="btn btn-youtube btn-light btn-icon"><i
		class="fab fa-youtube fa-fw"></i></a>'; } if
	(!empty($setting->tiktok_url)) { $social .= '
	<a href="'.$setting->tiktok_url.'" target="_blank"
		class="btn btn-tiktok btn-light btn-icon"><i
		class="fab fa-tiktok fa-fw"></i></a>'; } if
	(!empty($setting->instagram_url)) { $social .= '
	<a href="'.$setting->instagram_url.'" target="_blank"
		class="btn btn-instagram btn-light btn-icon"><i
		class="fab fa-instagram fa-fw"></i></a>'; } if
	(!empty($setting->linkedin_url)) { $social .= '
	<a href="'.$setting->linkedin_url.'" target="_blank"
		class="btn btn-linkedin btn-light btn-icon"><i
		class="fab fa-linkedin-in fa-fw"></i></a>'; } if
	(!empty($setting->twitter_url)) { $social .= '
	<a href="'.$setting->twitter_url.'" target="_blank"
		class="btn btn-twitter btn-light btn-icon"><i
		class="fab fa-twitter fa-fw"></i></a>'; } } if ($footer->subscribe) {
	$subscribe = '
	<form class="needs-validation" action="'.url('/subscribe').'"
		method="post">
		<div>
			<h4 class="mb-1">Join our newsletter</h4>
			<div class="row g-2">
				<div class="col-md-9 col-8">
					<label for="subscribe_email" class="visually-hidden">Email</label>
					<input type="email" class="form-control" id="subscribe_email"
						name="subscribe-email" placeholder="Email address" required>
					<div class="invalid-feedback">Please enter email.</div>
				</div>
				<div class="col-md-3 col-4">
					<button type="submit" class="btn btn-primary btn-subscribe">Subscribe</button>
				</div>
			</div>
		</div>
	</form>
	'; } if (!empty($footer_menu_items)) { $html = str_replace(
	['@LOGO_HREF', '@LOGO_IMAGE', '@MENU', '@SOCIAL', '@SITE_NAME',
	'@SUBSCRIBE'], [url('/'), Storage::url($setting->site_logo ?? ''),
	renderFooterMenuItems($footer_menu_items), $social,
	($setting->site_name ?? ''), $subscribe],
	($footer->module->html_content ?? ''), ); } } function
	renderFooterMenuItems($menu_items) { $hmtl = '
	<div class="row g-4">
		<div class="accordion accordion-flush d-sm-flex w-100"
			id="accordion-footer">
			'; foreach ($menu_items as $item) { $hmtl .= '
			<div class="col-12 col-sm">
				<div class="accordion-item border-0">
					<h5
						class="accordion-header footer-heading text-dark d-block d-sm-none"
						id="flush-heading-'.$item->id.'">
						<button class="accordion-button collapsed px-0 text-dark bg-white"
							type="button" data-bs-toggle="collapse"
							data-bs-target="#flush-collapse-'.$item->id.'"
							aria-expanded="false"
							aria-controls="flush-collapse-'.$item->id.'">'.$item->name.'</button>
					</h5>
					<h5
						class="accordion-header footer-heading mt-3 pb-2 d-none d-sm-block">'.$item->name.'</h5>
					<div id="flush-collapse-'.$item->id.'"
						class="accordion-collapse collapse d-sm-flex"
						aria-labelledby="flush-heading-'.$item->id.'"
						data-bs-parent="#accordion-footer">
						<div class="accordion-body px-0">
							<ul class="footer-nav list-unstyled">
								'; foreach ($item->children as $child) { $hmtl .= '
								<li class="nav-item"><a title=""
									href="'.($child->slug == App\Models\Page::DEFAULT_PAGE ? url('/') : $child->link).'">'.$child->name.'</a></li>';
								} $hmtl .= '
							</ul>
						</div>
					</div>
				</div>
			</div>
			'; } $hmtl .= '
		</div>
	</div>
	'; return $hmtl; } @endphp

	<footer> {!! $html !!} </footer>

	{{--
	<div class="container">
		<div class="row g-4">
			<div class="col-12 col-md-4">
				<div class="mb-4">
					<a href="@LOGO_HREF" class="text-inverse"><img class="logo"
						src="@LOGO_IMAGE"></a>
				</div>
				<p>Lorem ipsum dolor sit amet consectetur adipisicing elit.
					Cupiditate officiis necessitatibus minima ullam ipsam impedit quam
					id illum ut incidunt!</p>
				<div class="mt-4">@SUBSCRIBE</div>
			</div>
			<div class="col-12 col-md-8">@MENU</div>
		</div>
		<div class="mt-4">
			<div class="row align-items-center">
				<div class="col-md-9">
					<div class="small mb-3 mb-lg-0">
						Copyright © 2024 by <span class="text-primary"><a
							href="@LOGO_HREF">@SITE_NAME</a></span>. All rights reserved.
					</div>
				</div>
				<div class="col-md-3">
					<div
						class="text-md-end d-flex gap-2 align-items-center justify-content-md-end">@SOCIAL</div>
				</div>
			</div>
		</div>
	</div>
	--}}

</body>
</html>