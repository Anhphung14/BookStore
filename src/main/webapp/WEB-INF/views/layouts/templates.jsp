<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div class="offcanvas-template-secondary offcanvas offcanvas-end bg-light border-0 shadow" style="right: 300px; width:500px" data-bs-scroll="true" data-bs-backdrop="false" tabindex="-1" id="offcanvasPreview" aria-labelledby="offcanvasPreviewLabel">
	<div class="offcanvas-header">
		<h4 class="offcanvas-title" id="offcanvasPreviewLabel">Select and drag component to the editor</h4>
	</div>
	<div id="photoCanvas" class="offcanvas-body scrollbar">	
		<div class="input-group m-1">
			<input class="form-control search-input" name="search_text" id="search_text"  placeholder="Search ...">
			<button type="button" class="btn btn-light" id="search-button"><i class="fal fa-search"></i></button>
		</div>
		<div class="template-categories"></div>
	</div>
</div>

<div class="offcanvas-template offcanvas offcanvas-end bg-white border-0 shadow" style="width:300px" data-bs-scroll="true" data-bs-backdrop="false" tabindex="-1" id="offcanvasExample" aria-labelledby="offcanvasExampleLabel">
	<div class="offcanvas-header">
		<h4 class="offcanvas-title" id="offcanvasExampleLabel">Templates</h4>
		<button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
	</div>
	<div class="offcanvas-body scrollbar ">
		<div class="nav-lines">
			<ul class="nav nav-tabs" id="nav-tab">
				<li class="nav-item">
					<a class="nav-link active" id="nav-module-tab" data-bs-toggle="tab" data-bs-target="#nav-module" type="button" role="tab" aria-controls="nav-module" aria-selected="true"><i class="far fa-magic fa-fw text-muted me-2"></i><span class="fw-semibold">Modules</span></a>
				</li>
				<li class="nav-item">
					<a class="nav-link" id="nav-media-tab" data-bs-toggle="tab" data-bs-target="#nav-media" type="button" role="tab" aria-controls="nav-media" aria-selected="false"><i class="far fa-images fa-fw text-muted me-2"></i><span class="fw-semibold">Media</span></a>
				</li>
			</ul>
		</div>
		<div class="tab-content scrollbar mt-3">
			<!-- Content of Module -->
			<div id="nav-module" class="tab-pane fade show active" aria-labelledby="module-template-tab">
				<ul class="nav">
<!-- 					@foreach ($templates as $i => $template) -->
						<li class="nav-item nav-item-template w-100">
							<a class="nav-link text-body text-capitalize py-3 rounded" template-item=""></a>
						</li>
<!-- 					@endforeach -->
				</ul>
			</div>
			<div id="nav-media" class="tab-pane fade" aria-labelledby="media-template-tab">
				<ul class="nav">
					<li class="nav-item nav-item-media w-100">
						<a class="nav-link text-body text-capitalize py-3 rounded" data-type="photo">Photo</a>
					</li>
					<li class="nav-item nav-item-media w-100">
						<a class="nav-link text-body text-capitalize py-3 rounded" data-type="video">Video</a>
					</li>
					<li class="nav-item nav-item-media w-100">
						<a class="nav-link text-body text-capitalize py-3 rounded" data-type="file">File</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
</div>

<script>
	$(document).ready(function() {
		var hideTimeout;
		var currentPage = 1;
		var totalPages = 1;
		var typeMedia;
		var isAtEnd = false;
		var searchText;
		const originalHeight = $(".template-categories").height();
		const templates = {!! json_encode($templates) !!};
		
		// Module
		$(".nav-item-template .nav-link").on("mouseenter", function(e) {
			searchText = "";
			
			$(".input-group").hide();
			$("#search_text").val(searchText);
			
			$(".offcanvas-template-secondary").toggleClass("show", true);
			$(".template-categories").empty().scrollTop(0).height(originalHeight);
			$(".template-categories").removeAttr("data-masonry").removeClass("row grid g-3 mt-3");
			
			totalPages = 1;
			currentPage = 1;
			const key = $(e.target).attr("template-item");
			const childTemplates = templates[key].child;
			var template = "";
			
			if (childTemplates.length != 0) {
				childTemplates.forEach(child => {
					template += `
					<div class="card border-0 shadow mb-5">
						<div class="position-relative bg-white rounded-3 p-3">
							<div class="overflow-auto">
								<img 
									class="img-fluid" 
									src="{{ asset('storage/') }}/${child.thumbnail}" 
									alt="img" 
									data-value='${child.value.replace(/'/g, "&apos;").replace(/"/g, "&quot;")}'
								>
							</div>
						</div>
					</div>`;
				});
			}
			$(".template-categories").append(template);
		});
		
		// Media
		$(".nav-item-media .nav-link").on("mouseenter", function(e) {
			typeMedia = $(this).data("type");
			searchText = "";
			
			$(".input-group").show();
			$("#search_text").val(searchText);
			
			$(".offcanvas-template-secondary").toggleClass("show", true);
			$(".template-categories").empty().scrollTop(0).height(originalHeight);
			if (typeMedia == "photo") {
				$(".template-categories").attr("data-masonry", '{"percentPosition": true}');
			} else {
				$(".template-categories").removeAttr("data-masonry");
			}
			$(".template-categories").addClass("row grid g-3 mt-2");
			
			loadMedia();
		});
		
		// Hide offcanvas
		$(".tab-content").on({
			mouseenter: () => clearTimeout(hideTimeout),
			mouseleave: handleMouseLeave(".offcanvas-template-secondary", () => {
				$(".offcanvas-template-secondary").toggleClass("show", false);
			})
		});

		$(".offcanvas-template-secondary").on({
			mouseenter: () => clearTimeout(hideTimeout),
			mouseleave: handleMouseLeave(".offcanvas-template-secondary", () => {
				$(".offcanvas-template-secondary").toggleClass("show", false);
			})
		});

		// setData
		$(".template-categories").on("dragstart", "img", function(e) {
			e.originalEvent.dataTransfer.setData("text/html", $(this).data("value"));
		});

		$("#photoCanvas").on("scroll", function() {
			getScrollerEndPoint();
		});

		$("#search-button").on("click", function() {
			searchText = $("#search_text").val();
			loadMedia(1, searchText);
		});

		function loadMedia(page=1, searchText="") {
			$.ajax({
				url: "{{ route('saga.templates') }}",
				method: "GET",
				data: {
					_token: "{{ csrf_token() }}",
					page: page,
					type: typeMedia,
					search_text: searchText
				},
				success: function(response) {
					var templates = response.medias.data;
					var template = "";
					var imgValue = "";
					var fileName = "";
					if (templates.length !== 0) {
						templates.forEach(child => {
							if (typeMedia === "photo") {
								imgValue = `<img class="rounded" alt="${child.file_path}" src="{{ asset('storage/') }}/${child.file_path}" loading="lazy" >`;
								var baseName = getBaseName(child.file_path);
								template += `<div class="col-sm-6 grid-item">
									<div class="card border-0">
										<div class="position-relative">
											<img class="card-img-top rounded" alt="img" src="{{ asset('storage/') }}/${child.thumbnail ?? child.file_path}" loading="lazy" data-value='${imgValue.replace(/'/g, "&apos;").replace(/"/g, "&quot;")}'>
										</div>
										<div class="file-info position-absolute bottom-0 start-0 end-0 bg-dark bg-opacity-75 text-white d-flex justify-content-between align-items-center py-1 px-2 rounded-bottom">
											<span class="file-name crop-text-1">${baseName}</span>
											<span class="file-size text-nowrap">${child.file_size}</span>
										</div>
									</div>
								</div>`;
							} else if (typeMedia === "video") {
								imgValue = `<video class="rounded" alt="${child.file_path}" src="{{ asset('storage/') }}/${child.file_path}" data-fancybox="gallery" ></video>`;
								template += `<div class="col-sm-6 grid-item">
										<img src="{{ asset('assets/images/icons/files/mp4.svg') }}" 
											class="opacity-0"
											data-value='${imgValue.replace(/'/g, "&apos;").replace(/"/g, "&quot;")}'
											style="position: absolute; z-index: 1;">
										<a class="ratio ratio-16x9 " style=" z-index: -1;">
											<video class="rounded" src="{{ asset('storage/') }}/${child.file_path}" ></video>
										</a>
								</div>`;
							} else if (typeMedia === "file") {
								fileName = getFileName(child.file_path);
								var imgValue = `<a href="{{ asset('storage/') }}/${child.file_path}" >${fileName}</a>`;
								template += `
									<div class="d-flex align-items-center">
										<div class="avatar avatar-sm mx-3">
											<img src="{{ asset('${child.thumbnail}') }}" data-value='${imgValue.replace(/'/g, "&apos;").replace(/"/g, "&quot;")}'>
										</div>
										<p class="m-0 p-0 text-base" href="{{ asset('storage/') }}/${child.file_path}">${fileName}</p>
									</div>
								`;
							}
						});
						
						currentPage = response.medias.current_page;
						totalPages = response.totalPages;
						if (currentPage === 1 && $.trim($(".template-categories").html()).length > 0) {
							$(".template-categories").empty().scrollTop(0).height(originalHeight);
						}
						$(".template-categories").append(template);
						
						if (typeMedia === "photo") {
							resizeGridItems();
						}
					}
				}
			});
		}

		function handleMouseLeave(selector, callback) {
			return function() {
				clearTimeout(hideTimeout);
				hideTimeout = setTimeout(callback, 750);
			};
		}

		function getScrollerEndPoint() {
			const $photoCanvas = $("#photoCanvas");
			var scrollHeight = $photoCanvas.prop("scrollHeight");
			var divHeight = $photoCanvas.height();
			var scrollTop = $photoCanvas.scrollTop();
			if (scrollTop + divHeight >= scrollHeight - 150) {
				if (!isAtEnd) {
					isAtEnd = true;
					if(totalPages>=(currentPage + 1)) {
						loadMedia(currentPage + 1, searchText);
					}
				}
			} else {
				isAtEnd = false;
			}
		}
	});
	
	function resizeGridItems() {
		var masonryRow = document.querySelector(".row[data-masonry]");
		imagesLoaded(masonryRow, function() {
			new Masonry(masonryRow, {
				itemSelector: ".grid-item",
				percentPosition: true
			});
		});
	}
</script>
</body>
</html>